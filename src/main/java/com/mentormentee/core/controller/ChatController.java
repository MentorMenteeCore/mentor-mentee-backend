package com.mentormentee.core.controller;

import com.mentormentee.core.domain.User;
import com.mentormentee.core.dto.ChatMessageDto;
import com.mentormentee.core.dto.SendResponseDto;
import com.mentormentee.core.dto.UserLookAsideDto;
import com.mentormentee.core.service.ChatRoomService;
import com.mentormentee.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Controller
public class ChatController {


    private final SimpMessageSendingOperations messagingTemplate;
    private final UserService userService;
    private final ChatRoomService chatRoomService;

    /**
     * 채팅 방 열고
     * 메세지 입력하고 클릭하면 작동하는 컨트롤러
     *
     * 경로를 구독하고 있는 모든 사람에게
     * 메세지를 실시간으로 보낸다.
     *
     * 추가로 대화를 DB에 저장한다.
     */
    @MessageMapping("/chat/message")
    public void message(ChatMessageDto message) {
        Long otherId = ChatMessageDto.getOtherId(message);
        Long senderId = Long.valueOf(message.getSenderId());

        /**
         *  Redis LookAside -> 조회 성능 향상
         *
         * 2025 01-15
         * 최기연
         */
        //상대방 그리고 보낸사람 객체 찾기
        CompletableFuture<UserLookAsideDto> otherUserFuture = userService.getOtherUserObject(otherId);
        CompletableFuture<UserLookAsideDto> senderFuture = userService.getUserObject(senderId);
        LocalDateTime now = LocalDateTime.now();

        //thenAcceptBoth : 두개 작업 병렬결과 처리 & return 없음
        otherUserFuture.thenAcceptBoth(senderFuture, (otherUserObject, senderObject) -> {

            String userPicUrl = senderObject.getUserProfilePicture();
            boolean isUserInRoom = User.isUserInRoom(otherUserObject, message.getRoomId());

            messagingTemplate.convertAndSend("/sub/chat/room/"+message.getRoomId()
                    , new SendResponseDto(message.getRoomId()
                            ,message.getSenderId()
                            ,now
                            ,isUserInRoom
                            ,userPicUrl
                            ,message.getMessage()));

            chatRoomService.saveMessage(message.getMessage(),message.getRoomId(),now,isUserInRoom,Long.valueOf(message.getSenderId()));

        });
    }
}
