package com.ChatApp.controller;

import com.ChatApp.dto.CreateConversationRequest;
import com.ChatApp.entity.Conversation;
import com.ChatApp.service.ConversationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/conversations")
@RequiredArgsConstructor
public class ConversationController {

    private final ConversationService conversationService;

    @PostMapping
    public ResponseEntity<Conversation> createConversation(
            @Valid @RequestBody CreateConversationRequest request
    ) {

        Conversation conversation =
                conversationService.createConversation(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(conversation);
    }
}