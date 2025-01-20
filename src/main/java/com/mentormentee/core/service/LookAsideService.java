package com.mentormentee.core.service;

import com.mentormentee.core.domain.ChatRoom;
import com.mentormentee.core.domain.User;
import com.mentormentee.core.dto.UserLookAsideDto;
import com.mentormentee.core.repository.ChatRoomRepository;
import com.mentormentee.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LookAsideService {

    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Cacheable(cacheNames = "getChatUser", key="'completable:user:'+#userId",cacheManager = "defaultCacheManager")
    public UserLookAsideDto getUser(Long userId) {
        User byId = userRepository.findById(userId);
        return UserLookAsideDto.builder()
                .userCurrentAccessedChatRoom(byId.getUserCurrentAccessedChatRoom())
                .userProfilePicture(byId.getUserProfilePicture()).build();
    }

    @Cacheable(cacheNames = "getChatRoomId", key="'Chatroom:room:'+#roomId",cacheManager = "longCacheManager")
    public Long findRoomId(String roomId) {
        ChatRoom cr = chatRoomRepository.findRoomByRoomId(roomId);
        return cr.getId();
    }

}
