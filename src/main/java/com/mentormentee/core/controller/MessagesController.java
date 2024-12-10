package com.mentormentee.core.controller;

import com.mentormentee.core.domain.ChatRoom;
import com.mentormentee.core.dto.*;
import com.mentormentee.core.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MessagesController {

    private final ChatRoomService chatRoomService;

    //상대방 없다면 0보내기
    @GetMapping("/message")
    private ResponseEntity<MessagesDto> getRoomListAndMainRoomMessagesIfExist(@RequestParam(name="opponentId",required = false, defaultValue = "0") String opponentId, @RequestParam(name="thisUserId") String thisUserId){

        if(opponentId.equals("0")){
            MessagesDto messagesDto = new MessagesDto();
            Long currentUserId = Long.valueOf(thisUserId);
            List<ChatRoomDetailsDTO> chatRoomsListWithNoMainRoom = chatRoomService.getChatRoomsListWithNoMainRoom(currentUserId);
            messagesDto.setChatRooms(chatRoomsListWithNoMainRoom);
            return ResponseEntity.ok(messagesDto);
        }else{
            String roomId = ChatRoom.getRoomId(opponentId, thisUserId);
            Long currentUserId = Long.valueOf(thisUserId);
            CreateChatRoomDto createChatRoomDto = new CreateChatRoomDto(thisUserId,opponentId);

            RoomDto roomInfo = RoomDto.createRoomDto(createChatRoomDto);
            chatRoomService.checkIfRoomExist(roomInfo);
            List<MessageDetailsDTO> messagesInChatRoom = chatRoomService.getMessagesInChatRoom(roomId, currentUserId);
            List<ChatRoomDetailsDTO> chatRoomsListWithNoMainRoom = chatRoomService.getChatRoomsListWithNoMainRoom(currentUserId);
            MessagesDto messagesDto = new MessagesDto();
            messagesDto.setChatRooms(chatRoomsListWithNoMainRoom);
            messagesDto.setChatMessages(messagesInChatRoom);
            return ResponseEntity.ok(messagesDto);
        }

    }

    @PostMapping("/room")
    public ResponseEntity<?> createRoom(@RequestBody CreateChatRoomDto chatRoomDto){
        RoomDto roomInfo = RoomDto.createRoomDto(chatRoomDto);
        chatRoomService.createRoom(roomInfo);
        return ResponseEntity.ok(new ResponseCode(200));
    }

    @DeleteMapping("/room")
    public ResponseEntity<?> createRoom(@RequestBody DeleteRoomId deleteRoomId){
        chatRoomService.deleteRoom(deleteRoomId.getRoomId());
        return ResponseEntity.ok(new ResponseCode(200));
    }

}
