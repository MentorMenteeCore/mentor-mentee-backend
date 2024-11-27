package com.mentormentee.core.repository;

import com.mentormentee.core.domain.ChatRoom;
import com.mentormentee.core.dto.ChatRoomDetailsDTO;
import com.mentormentee.core.dto.MessageDetailsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<ChatRoom,Long> {

    @Query(value = """
        SELECT
            cr.room_id AS roomId,
            lm.content AS lastMessageContent,
            lm.time AS lastMessageTime,
            u.nick_name AS otherUserNickname,
            u.user_profile_picture AS otherUserProfilePicture,
            u.user_id AS otherUserId,
            COALESCE(uc.unread_count, 0) AS unreadCount
        FROM
            chat_room cr
        LEFT JOIN
            (
                SELECT
                    m1.chat_room_id,
                    m1.content,
                    m1.time
                FROM
                    message m1
                INNER JOIN
                    (
                        SELECT
                            chat_room_id,
                            MAX(time) AS max_time
                        FROM
                            message
                        GROUP BY
                            chat_room_id
                    ) m2 ON m1.chat_room_id = m2.chat_room_id AND m1.time = m2.max_time
            ) lm ON cr.chat_rooom_id = lm.chat_room_id
        LEFT JOIN
            (
                SELECT
                    m.chat_room_id,
                    COUNT(*) AS unread_count
                FROM
                    message m
                WHERE
                    m.read_or_not = FALSE
                    AND m.sender_id != :currentUserId
                GROUP BY
                    m.chat_room_id
            ) uc ON cr.chat_rooom_id = uc.chat_room_id
        JOIN
            users u ON u.user_id = CASE WHEN cr.first_user_id = :currentUserId THEN cr.second_user_id ELSE cr.first_user_id END
        WHERE
            cr.first_user_id = :currentUserId OR cr.second_user_id = :currentUserId
        """, nativeQuery = true)
    List<ChatRoomDetailsDTO> findChatRoomsWithDetails(@Param("currentUserId") Long currentUserId);

    @Query(value = """
        SELECT
            m.message_id AS messageId,
            m.content AS content,
            m.time AS time,
            m.read_or_not AS readOrNot,
            u.nick_name AS senderNickname,
            u.user_profile_picture AS senderProfilePicture,
            CASE WHEN m.sender_id = :currentUserId THEN TRUE ELSE FALSE END AS isCurrentUser
        FROM
            message m
        JOIN
            users u ON m.sender_id = u.user_id
        JOIN
            chat_room cr ON m.chat_room_id = cr.chat_rooom_id
        WHERE
            cr.room_id = :roomId
        ORDER BY
            m.time ASC
        """, nativeQuery = true)
    List<MessageDetailsDTO> findMessagesByRoomId(@Param("roomId") String roomId, @Param("currentUserId") Long currentUserId);

    @Modifying
    @Query("DELETE FROM ChatRoom c WHERE c.roomId = :deleteRoomId")
    void deleteRoomByRoomId(String deleteRoomId);

}