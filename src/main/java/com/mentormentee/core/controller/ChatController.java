package com.mentormentee.core.controller;

import com.mentormentee.core.dto.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatController {


    private final SimpMessageSendingOperations messagingTemplate;

    /**
     * 채팅 방 열고
     * 메세지 입력하고 클릭하면 작동하는 컨트롤러
     *
     * 경로를 구독하고 있는 모든 사람에게
     * 메세지를 실시간으로 보낸다.
     *
     * 추가로 대화를 DB에 저장한다.
     *
     */
    @MessageMapping("/chat/message")
    public void message(ChatMessageDto message) {
        messagingTemplate.convertAndSend("/sub/chat/room/"
                        + message.getMentorId()
                        + "/"
                        + message.getMenteeId()
                        , message);


    }

}
