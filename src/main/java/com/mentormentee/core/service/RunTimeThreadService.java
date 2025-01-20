package com.mentormentee.core.service;

import com.mentormentee.core.domain.ChatRoom;
import com.mentormentee.core.domain.Message;
import com.mentormentee.core.domain.User;
import com.mentormentee.core.dto.MessageSaveDto;
import com.mentormentee.core.repository.ChatRoomRepository;
import com.mentormentee.core.repository.MessageRepository;
import com.mentormentee.core.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

@Service
@Transactional
public class RunTimeThreadService {

    private final LinkedBlockingQueue<MessageSaveDto> syncQueue = new LinkedBlockingQueue<>();
    private final LookAsideService lookAsideService;
    private final ThreadPoolTaskExecutor runTimeThreadExecutor;
    private final List<MessageSaveDto> messagesComponentsThread1 = new ArrayList<>();
    private final List<Message> finalMessagesThread1 = new ArrayList<>();
    private final MessageRepository messageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private Runnable runnable1;


    public RunTimeThreadService(LookAsideService lookAsideService
            , @Qualifier("myFixedThreadPool") ThreadPoolTaskExecutor runTimeThreadExecutor
            , MessageRepository messageRepository
            , ChatRoomRepository chatRoomRepository
            , UserRepository userRepository) {
        this.lookAsideService = lookAsideService;
        this.runTimeThreadExecutor = runTimeThreadExecutor;
        this.messageRepository = messageRepository;
        this.chatRoomRepository = chatRoomRepository;
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void init() {

        runnable1 = () -> {
            while (true) {
                try {
                    syncQueue.drainTo(messagesComponentsThread1, 800);
                    if (!messagesComponentsThread1.isEmpty()) {
                        messagesComponentsThread1.stream().forEach(a -> {
                            ChatRoom proxyCr = chatRoomRepository.findProxyChatRoom(lookAsideService.findRoomId(a.roomId));
                            User proxyUser = userRepository.findProxyUserByUserId(a.senderId);

                            Message senderMessage = new Message();
                            senderMessage.createMessage(a.message, proxyCr, a.now, a.isUserInRoom, proxyUser);
                            finalMessagesThread1.add(senderMessage);
                        });
                        messageRepository.saveAll(finalMessagesThread1);
                        finalMessagesThread1.clear();
                        messagesComponentsThread1.clear();

                    } else {
                        //0.3초 대기
                        Thread.sleep(300);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
        };

        runTimeThreadExecutor.execute(runnable1);
    }

    @PreDestroy
    public void destroy() throws InterruptedException {
        syncQueue.wait(500);
        runTimeThreadExecutor.shutdown();
    }

    @Async
    public void addMessage(String message, String roomId, LocalDateTime now, boolean userInRoom, Long senderId) {
        syncQueue.offer(new MessageSaveDto(message, roomId, now, userInRoom, senderId));
    }
}
