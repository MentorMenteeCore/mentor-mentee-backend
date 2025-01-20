package com.mentormentee.core.controller;

import com.mentormentee.core.domain.User;
import com.mentormentee.core.dto.ChatMessageDto;
import com.mentormentee.core.dto.MessageSaveDto;
import com.mentormentee.core.dto.SendResponseDto;
import com.mentormentee.core.dto.UserLookAsideDto;
import com.mentormentee.core.service.ChatRoomService;
import com.mentormentee.core.service.RunTimeThreadService;
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
    private final RunTimeThreadService runTimeThreadService;

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
         *  V2
         *  Redis LookAside -> 조회 성능 향상
         *  2025 01-15
         * =======
         *  V3
         *  Redis LookAside & 스레드가 런타임동안 계속 돌아가면서 배치 작업 담당
         *  2025-01-18
         */
        //상대방 그리고 보낸사람 객체 찾기
        UserLookAsideDto other = userService.getOtherUserObject(otherId);
        UserLookAsideDto sender = userService.getUserObject(senderId);
        LocalDateTime now = LocalDateTime.now();

        String picUrl = sender.getUserProfilePicture();
        boolean userInRoom = User.isUserInRoom(other, message.getRoomId());
        runTimeThreadService.addMessage(message.getMessage(),message.getRoomId(),now,userInRoom,Long.valueOf(message.getSenderId()));
        messagingTemplate.convertAndSend("/sub/chat/room/"+message.getRoomId()
                    , new SendResponseDto(message.getRoomId()
                            ,message.getSenderId()
                            ,now
                            ,userInRoom
                            ,picUrl
                            ,message.getMessage()));

    }
}
