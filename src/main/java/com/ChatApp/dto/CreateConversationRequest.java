package com.ChatApp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateConversationRequest {

    @NotNull
    private Long userId;

    @NotNull
    private Long targetUserId;
}