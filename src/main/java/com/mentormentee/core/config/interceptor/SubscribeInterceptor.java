package com.mentormentee.core.config.interceptor;

import com.mentormentee.core.domain.User;
import com.mentormentee.core.dto.SendUserJoinedDto;
import com.mentormentee.core.repository.UserRepository;
import com.mentormentee.core.service.ChatRoomService;
import com.mentormentee.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SubscribeInterceptor implements ChannelInterceptor {

    private final ChatRoomService chatRoomService;
    private final UserService userService;
    private final UserRepository userRepository;
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    public void setMessagingTemplate(@Lazy SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }


    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            List<String> userIds = accessor.getNativeHeader("user-id");
            if (userIds != null && !userIds.isEmpty()) {
                Long userId = Long.valueOf(userIds.get(0));
                accessor.getSessionAttributes().put("user-id", userId); // 세션에 사용자 ID 저장
            }
        }

        if (StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
            List<String> userIds = accessor.getNativeHeader("user-id");


            if (userIds != null && !userIds.isEmpty()) {
                Long userId = Long.valueOf(userIds.get(0));
                List<String> userSubscribedRooms = chatRoomService.getUserSubscribedRooms(userId);
                String destination = accessor.getDestination();
                sendUserJoined(destination, userId);

                userSubscribedRooms.forEach(room -> {
                    // room을 "smallId/bigId" 형식으로 파싱
                    String[] userIdsInRoom = room.split("/"); //"smallId/bigId"
                    if (userIdsInRoom.length == 2) {
                        Long smallId = Long.valueOf(userIdsInRoom[0]);
                        Long bigId = Long.valueOf(userIdsInRoom[1]);

                        // 구독 경로를 생성
                        String newDestination = "/sub/chat/room/" + smallId + "/" + bigId;

                        // 브로커에 구독 요청 보내기
                        //수정 생각해봐
                        String[] coreRoomId = destination.split("room/");
                        userService.insertCoreRoomIdToUser(coreRoomId[1], userId);

                        // destination : /sub/chat/room/2/3 이런식으로 담긴다.
                        if (!destination.equals(newDestination)) {
                            sendSubscriptionToBroker(newDestination, accessor.getSessionId(),channel);
                        }
                    }
                });
            }

        }

        if (StompCommand.DISCONNECT.equals(accessor.getCommand())) {
            Long userId = (Long) accessor.getSessionAttributes().get("user-id"); // 세션에서 사용자 ID 가져오기
            if (userId != null) {
                userService.setNullRoomToUser(userId);
            }
        }

        return message; // 원래 요청을 그대로 브로커로 전달
    }

    private void sendUserJoined(String destination, Long userId) {
        String otherId = getOtherId(destination, String.valueOf(userId));
        User other = userRepository.findById(Long.valueOf(otherId));
        String otherUserCurrentAccessedChatRoom = other.getUserCurrentAccessedChatRoom();
        if (otherUserCurrentAccessedChatRoom==(null)) {
            return;
        }

        if (destination.equals("/sub/chat/room/"+otherUserCurrentAccessedChatRoom)) {
            messagingTemplate.convertAndSend(destination,  new SendUserJoinedDto(String.valueOf(userId), userId + "번의 유저가 입장하였습니다"));
        }
    }

    private String getOtherId(String destination, String userId) {
        String[] roomId = destination.split("room/");
        String room = roomId[1];
        String[] userAndOtherId = room.split("/");
        if(userAndOtherId[0].equals(userId)) {
            return userAndOtherId[1];
        }else{
            return userAndOtherId[0];
        }

    }


    private void sendSubscriptionToBroker(String destination, String sessionId, MessageChannel channel) {
        // 브로커로 새로운 구독 요청 보내기
        StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.SUBSCRIBE);
        accessor.setSessionId(sessionId);
        accessor.setDestination(destination);

        String subscriptionId = UUID.randomUUID().toString(); // 고유 ID 생성
        accessor.setSubscriptionId(subscriptionId);

        Message<byte[]> newMessage = MessageBuilder.createMessage(new byte[0], accessor.getMessageHeaders());
        channel.send(newMessage);
    }
}
