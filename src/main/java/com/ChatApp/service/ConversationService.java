package com.ChatApp.service;

import com.ChatApp.dto.CreateConversationRequest;
import com.ChatApp.entity.Conversation;
import com.ChatApp.entity.ConversationMember;
import com.ChatApp.entity.User;
import com.ChatApp.repository.ConversationMemberRepository;
import com.ChatApp.repository.ConversationRepository;
import com.ChatApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConversationService {

    private final ConversationRepository conversationRepository;
    private final ConversationMemberRepository conversationMemberRepository;
    private final UserRepository userRepository;

    @Transactional
    public Conversation createConversation(
            CreateConversationRequest request
    ) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        User targetUser = userRepository.findById(request.getTargetUserId())
                .orElseThrow(() ->
                        new RuntimeException("Target user not found")
                );

        Conversation conversation = Conversation.builder()
                .build();

        conversationRepository.save(conversation);

        ConversationMember member1 = ConversationMember.builder()
                .conversation(conversation)
                .user(user)
                .build();

        ConversationMember member2 = ConversationMember.builder()
                .conversation(conversation)
                .user(targetUser)
                .build();

        conversationMemberRepository.save(member1);
        conversationMemberRepository.save(member2);

        return conversation;
    }
}