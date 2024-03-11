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
    private String Author;

    @NotBlank
    @NotNull
    private String Title;

    @NotBlank
    @NotNull
    private String Content;

}
