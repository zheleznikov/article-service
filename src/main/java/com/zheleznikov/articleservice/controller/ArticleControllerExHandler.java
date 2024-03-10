package com.zheleznikov.articleservice.controller;

import com.zheleznikov.articleservice.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = {ArticleController.class})
@Slf4j
public class ArticleControllerExHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ErrorResponse handleArticleServiceEx(Exception e) {
        log.error("Error: {}", e.getMessage());
        return new ErrorResponse(e.getMessage());
    }
}
