package com.mentormentee.core.repository;

import com.mentormentee.core.domain.ChatRoom;
import com.mentormentee.core.domain.Message;
import com.mentormentee.core.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Modifying
    @Query("update Message m " +
            "set m.user = :newUser " +
            "where m.id in :messageIds")
    void updateMessageUserId( @Param("messageIds") List<Long> messageIds,
                              @Param("newUser") User newUser
    );

    @Modifying
    @Query(value = """
        UPDATE message m
        SET m.read_or_not = TRUE
        WHERE
            m.chat_room_id = (
                SELECT cr.chat_rooom_id FROM chat_room cr WHERE cr.room_id = :roomId
            )
            AND m.sender_id != :currentUserId
            AND m.read_or_not = FALSE
        """, nativeQuery = true)
    int markMessagesAsRead(@Param("roomId") String roomId, @Param("currentUserId") Long currentUserId);

    @Modifying
    @Query("DELETE FROM Message m WHERE m.chatRoom.roomId = :deleteRoomId")
    void deleteMessageByRoomId(String deleteRoomId);

    void deleteMessageByUser(User user);

    @Async
    CompletableFuture<List<Message>> getAllByUser(User user);

    @Modifying
    @Query("DELETE FROM Message m WHERE m.chatRoom IN :rooms")
    void deleteByChatRoomIn(@Param("rooms") List<ChatRoom> rooms);
}