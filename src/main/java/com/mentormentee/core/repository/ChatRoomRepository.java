package com.mentormentee.core.repository;

import com.mentormentee.core.domain.ChatRoom;
import com.mentormentee.core.domain.Message;
import com.mentormentee.core.dto.ChatSummaryDto;
import com.mentormentee.core.dto.OtherChatRoomsDto;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ChatRoomRepository {

    private final EntityManager em;

    public Optional<List<String>> findUserJoinedRooms(Long id) {
        List<String> resultList = em.createQuery("SELECT r.roomId FROM ChatRoom r WHERE r.firstUserId = :id OR r.secondUserId = :id", String.class)
                .setParameter("id", id)
                .getResultList();

        return Optional.ofNullable(resultList.isEmpty() ? null : resultList);

    }

    public void createNewChatRoom(ChatRoom chatRoom){
        em.persist(chatRoom);
    }

    public ChatRoom findRoomByRoomId(String id) {
        try {
            return em.createQuery("SELECT r FROM ChatRoom r WHERE r.roomId = :id", ChatRoom.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Async
    public CompletableFuture<List<ChatRoom>> findUserJoinedRoomsByUserId(Long id) {
         List<ChatRoom> chatRooms = em.createQuery("SELECT r FROM ChatRoom r WHERE r.firstUserId = :id or r.secondUserId = :id", ChatRoom.class)
                                        .setParameter("id", id)
                                        .getResultList();
        return CompletableFuture.completedFuture(chatRooms);
    }

    public void deleteAll(List<ChatRoom> userJoinedRooms) {
        List<Long> roomIds = userJoinedRooms.stream().map(ChatRoom::getId).toList();

        em.createQuery("DELETE FROM ChatRoom r WHERE r.id IN :roomIds")
                .setParameter("roomIds", roomIds)
                .executeUpdate();
    }

    public void saveAll(List<ChatRoom> userJoinedRooms) {

        for (ChatRoom userJoinedRoom : userJoinedRooms) {
            em.merge(userJoinedRoom);
        }

    }
}
