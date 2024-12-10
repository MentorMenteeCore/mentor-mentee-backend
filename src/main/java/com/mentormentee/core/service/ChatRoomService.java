package com.mentormentee.core.service;

import com.mentormentee.core.domain.ChatRoom;
import com.mentormentee.core.domain.Message;
import com.mentormentee.core.domain.User;
import com.mentormentee.core.dto.ChatRoomDetailsDTO;
import com.mentormentee.core.dto.MessageDetailsDTO;
import com.mentormentee.core.dto.RoomDto;
import com.mentormentee.core.exception.exceptionCollection.RoomDoesNotExistException;
import com.mentormentee.core.repository.ChatRepository;
import com.mentormentee.core.repository.ChatRoomRepository;
import com.mentormentee.core.repository.MessageRepository;
import com.mentormentee.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;

    public List<String> getUserSubscribedRooms(Long userId) {
        Optional<List<String>> userJoinedRooms = chatRoomRepository.findUserJoinedRooms(userId);
        if (userJoinedRooms.isPresent()) {
            return userJoinedRooms.get();
        }
        return null;
    }

    public List<ChatRoomDetailsDTO> getChatRoomsListWithNoMainRoom(Long currentUserId) {
        return chatRepository.findChatRoomsWithDetails(currentUserId);
    }


    @Transactional
    public List<MessageDetailsDTO> getMessagesInChatRoom(String roomId, Long currentUserId) {

        // 상대방이 보낸 메시지의 readOrNot 필드를 true로 업데이트
        messageRepository.markMessagesAsRead(roomId, currentUserId);

        // 메시지 조회
        List<MessageDetailsDTO> messages = chatRepository.findMessagesByRoomId(roomId, currentUserId);

        User user = userRepository.findById(currentUserId);
        user.setUserCurrentAccessedChatRoom(roomId);

        return messages;
    }

    @Transactional
    public void createRoom(RoomDto roomInfo) {
        String roomId = roomInfo.getRoomId();
        if(chatRoomRepository.findRoomByRoomId(roomId)==null){
            ChatRoom chatRoom = new ChatRoom(LocalDateTime.now(), roomInfo.getRoomId(), roomInfo.getUserId(), roomInfo.getOtherId());
            chatRoomRepository.createNewChatRoom(chatRoom);
        }
    }

    public void checkIfRoomExist(RoomDto roomInfo) {
        String roomId = roomInfo.getRoomId();
        if(chatRoomRepository.findRoomByRoomId(roomId)==null){
            throw new RoomDoesNotExistException();
        }
    }

    @Transactional
    public void saveMessage(String message, String roomId, LocalDateTime now, boolean isUserInRoom, Long senderId) {
        ChatRoom room = chatRoomRepository.findRoomByRoomId(roomId);
        User user = userRepository.findById(senderId);

        Message senderMessage = new Message();
        messageRepository.save(senderMessage);
        Message result = senderMessage.createMessage(message, room, now, isUserInRoom, user);
    }

    @Transactional
    public void deleteRoom(String deleteRoomId) {
        messageRepository.deleteMessageByRoomId(deleteRoomId);
        chatRepository.deleteRoomByRoomId(deleteRoomId);
    }
}
