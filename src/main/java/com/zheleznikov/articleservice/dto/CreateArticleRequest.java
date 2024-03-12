package com.zheleznikov.articleservice.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class CreateArticleRequest {

    @NotBlank
    @NotNull
    private LocalDate publishingDate;

    @NotBlank
    @NotNull
    private String author;

    @NotBlank
    @NotNull
    private String title;

    @NotBlank
    @NotNull
    private String content;

}
