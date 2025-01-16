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

    @Cacheable(cacheNames = "getChatUserRoom", key="'room:'+#roomId",cacheManager = "defaultCacheManager")
    public ChatRoom findRoom(String roomId) {
        return chatRoomRepository.findRoomByRoomId(roomId);
    }

    @Cacheable(cacheNames = "getChatUserObj", key="'user:'+#senderId",cacheManager = "defaultCacheManager")
    public User findSender(Long senderId) {
        return userRepository.findById(senderId);
    }


}
