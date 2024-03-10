package com.zheleznikov.articleservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.zheleznikov.articleservice.dto.CreateArticleRequest;
import com.zheleznikov.articleservice.dto.CreateArticleResponse;
import com.zheleznikov.articleservice.entity.Article;
import com.zheleznikov.articleservice.service.ArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ArticleControllerTest {

    private ArticleService articleService;

    private ArticleController articleController;

    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @BeforeEach
    public void setup() {
        articleService = mock(ArticleService.class);
        articleController = new ArticleController(articleService);
        mockMvc = MockMvcBuilders.standaloneSetup(articleController).build();
    }

    @Test
    public void testShouldCreateAnArticle() throws Exception {
        CreateArticleRequest request = new CreateArticleRequest()
                .setPublishingDate(LocalDate.now())
                .setContent("content")
                .setAuthor("author")
                .setTitle("title");

        CreateArticleResponse response = new CreateArticleResponse()
                .setArticleId(UUID.randomUUID())
                .setCreatedAt(LocalDateTime.now())
                .setMessage("success");

        when(articleService.createArticle(request)).thenReturn(response);

        mockMvc.perform(post("/api/v1/create-article")
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    public void testShouldGetAnArticleList() throws Exception {
        List<Article> articles = List.of(
                new Article().setArticleId(UUID.randomUUID()).setContent("content").setTitle("Title")
                        .setAuthor("Author").setPublishingDate(LocalDate.now()),
                new Article().setArticleId(UUID.randomUUID()).setContent("content").setTitle("Title")
                        .setAuthor("Author").setPublishingDate(LocalDate.now())
        );

        when(articleService.getArticles(0,2)).thenReturn(new PageImpl<>(articles));

        mockMvc.perform(get("/api/v1/article-list"))
                .andExpect(status().isOk());
    }

    @Test
    public void testShouldGetNumberOfArticles() throws Exception {
        when(articleService.getNumberOfArticlesPublishedOnDailyBasesFor7Days()).thenReturn(5L);

        mockMvc.perform(get("/inner-api/number-of-articles-last-7-days"))
                .andExpect(status().isOk());

    }




}
