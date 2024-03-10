package com.zheleznikov.articleservice.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class CreateArticleResponse {

    private UUID articleId;
    private String message;
    private LocalDateTime createdAt;
}
