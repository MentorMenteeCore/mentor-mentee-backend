package com.mentormentee.core.repository;

import com.mentormentee.core.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MessageRepository extends JpaRepository<Message, Long> {

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
}