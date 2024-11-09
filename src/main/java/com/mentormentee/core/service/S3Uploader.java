package com.mentormentee.core.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.mentormentee.core.domain.User;
import com.mentormentee.core.repository.UserRepository;
import com.mentormentee.core.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
@Component
public class S3Uploader {
    private final AmazonS3 amazonS3;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${spring.defaultProfileImage}")
    private String defaultProfileImage;

    // 프로필 이미지 업로드
    @Transactional
    public String uploadProfileImage(MultipartFile file) throws IOException {
        String userEmail = jwtUtils.getUserEmail();  // JWT에서 사용자 이메일 추출
        User requestUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("유저가 존재하지 않습니다."));

        // 현재 프로필 이미지가 기본 이미지가 아닌 경우, 기존 이미지 삭제
        String currentProfileUrl = requestUser.getUserProfilePicture();
        if (!currentProfileUrl.equals(defaultProfileImage)) {
            deleteImageFromS3(currentProfileUrl);  // 기존 이미지
        }

        // 새 이미지를 S3에 업로드 및 url 업데이트
        String newProfileUrl = upload(file, "profile");
        requestUser.setUserProfilePicture(newProfileUrl); // 새 이미지 URL로 사용자 프로필 업데이트

        return newProfileUrl;  // 새 프로필 이미지 URL 반환
    }

    // S3에 파일 업로드
    private String upload(MultipartFile multipartFile, String dirName) throws IOException {
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 전환 실패"));
        return uploadFile(uploadFile, dirName);
    }

    // 파일 S3에 저장 및 URL 반환
    private String uploadFile(File uploadFile, String dirName) {
        String userEmail = jwtUtils.getUserEmail(); // JWT에서 사용자 이메일 추출
        String fileName = dirName + "/" + userEmail + "_" + UUID.randomUUID() + uploadFile.getName();
        String uploadImageUrl = putS3(uploadFile, fileName);

        removeNewFile(uploadFile);  // 로컬 파일 삭제

        return uploadImageUrl;
    }

    // S3에 파일 저장
    private String putS3(File uploadFile, String fileName) {
        amazonS3.putObject(new PutObjectRequest(bucket, fileName, uploadFile));
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    // 로컬에 생성된 파일 삭제
    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("파일 삭제가 완료되었습니다.");
        } else {
            log.info("파일 삭제가 실패되었습니다.");
        }
    }

    // MultipartFile -> File 변환
    private Optional<File> convert(MultipartFile file) throws IOException {
        // 업로드된 파일이 비어 있는지 확인
        if (file.isEmpty()) {
            log.error("업로드된 파일이 비어 있습니다.");
            throw new IllegalArgumentException("업로드된 파일이 비어 있습니다.");
        }

        log.info("업로드된 파일 크기: " + file.getSize());

        String filePath = System.getProperty("java.io.tmpdir") +
                System.getProperty("file.separator") +
                file.getOriginalFilename();

        log.info("변환 중인 파일 경로: " + filePath);  // 경로 로그 추가

        File convertFile = new File(filePath);

        if (convertFile.exists()) {
            log.warn("파일이 이미 존재합니다: " + convertFile.getAbsolutePath());
        }

        if (convertFile.createNewFile()) {
            log.info("파일 생성 성공: " + convertFile.getAbsolutePath());
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        } else {
            log.error("파일 생성 실패: " + convertFile.getAbsolutePath());
            throw new IOException("파일 생성에 실패했습니다. 경로: " + convertFile.getAbsolutePath());
        }
    }

    // S3에서 이미지 삭제
    private void deleteImageFromS3(String imageUrl) {
        // imageUrl에서 profile/ 폴더 이후의 키를 추출
        String s3Key = imageUrl.substring(imageUrl.indexOf("profile/"));

        log.info("S3에서 삭제할 이미지 키: {}", s3Key); // 삭제할 키 로그

        try {
            // 삭제 요청 전 로그
            log.info("S3에서 이미지 삭제 요청을 전송합니다: {}", s3Key);

            // 이미지 삭제 요청
            amazonS3.deleteObject(new DeleteObjectRequest(bucket, s3Key));

            // 삭제 요청 후 로그
            log.info("S3에서 이전 프로필 이미지를 삭제했습니다: {}", s3Key);
        } catch (AmazonServiceException e) {
            log.error("S3에서 이미지 삭제 중 오류 발생: {}", e.getErrorMessage());
        } catch (Exception e) {
            log.error("S3에서 이미지 삭제 중 예상치 못한 오류 발생: {}", e.getMessage());
        }
    }

}


