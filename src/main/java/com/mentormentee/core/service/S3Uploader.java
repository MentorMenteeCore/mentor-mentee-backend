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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class S3Uploader {
    private final AmazonS3 amazonS3;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${defaultProfileImage}")
    private String defaultProfileImage;

    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 전환 실패"));
        return uploadFile(uploadFile, dirName);
    }

    private String uploadFile(File uploadFile, String dirName) {
        String userEmail = jwtUtils.getUserEmail(); // JWT를 통해 사용자 이메일 추출
        String fileName = dirName + "/" + userEmail + "_" + UUID.randomUUID() + uploadFile.getName();
        String uploadImageUrl = putS3(uploadFile, fileName);

        removeNewFile(uploadFile);  // 로컬 파일 삭제

        return uploadImageUrl;
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3.putObject(
                new PutObjectRequest(bucket, fileName, uploadFile)
                        .withCannedAcl(CannedAccessControlList.PublicRead)
        );
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("파일 삭제가 완료되었습니다.");
        } else {
            log.info("파일 삭제가 실패되었습니다.");
        }
    }

    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(System.getProperty("java.io.tmpdir") +
                System.getProperty("file.separator") +
                file.getOriginalFilename());

        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }

    @Transactional
    public String uploadProfileImage(MultipartFile file) throws IOException {
        String userEmail = jwtUtils.getUserEmail();  // JWT를 통해 사용자 이메일 추출
        User requestUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("유저가 존재하지 않습니다."));

        // S3에 파일을 업로드하고 URL을 받아옴
        String profileUrl = upload(file, "profile");
        requestUser.setProfileUrl(profileUrl); // 받아온 URL로 사용자 프로필 이미지 업데이트

        return "프로필 사진 업로드에 성공하였습니다.";
    }



    @Transactional
    public String deleteProfileImage() {
        String userEmail = jwtUtils.getUserEmail();  // JWT를 통해 사용자 이메일 추출
        User requestUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("유저가 존재하지 않습니다."));

        String currentProfileUrl = requestUser.getProfileUrl();  // 현재 프로필 이미지 URL 추출

        // 사용자의 프로필 이미지가 기본 이미지인 경우, 삭제 기능 비활성화
        if (currentProfileUrl.equals(defaultProfileImage)) {
            log.info("기본 프로필 이미지는 삭제할 수 없습니다.");
            return "기본 프로필 이미지는 삭제할 수 없습니다.";
        }

        // 프로필 이미지의 S3 키 추출
        String s3Key = currentProfileUrl.substring(currentProfileUrl.indexOf("profile/"));

        // S3에서 해당 이미지를 삭제
        try {
            amazonS3.deleteObject(new DeleteObjectRequest(bucket, s3Key));
            log.info("S3에서 프로필 이미지를 삭제했습니다.");
        } catch (AmazonServiceException e) {
            log.error("S3에서 이미지 삭제 중 오류 발생: {}", e.getErrorMessage());
            throw new IllegalStateException("프로필 이미지 삭제에 실패했습니다.");
        }

        // 프로필 이미지 URL을 기본 이미지로 재설정
        requestUser.setProfileUrl(defaultProfileImage);
        userRepository.save(requestUser);

        return "프로필 이미지가 기본 이미지로 재설정되었습니다.";
    }

}

