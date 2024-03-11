package com.zheleznikov.articleservice.service;

import com.zheleznikov.articleservice.dto.CreateArticleRequest;
import com.zheleznikov.articleservice.dto.CreateArticleResponse;
import com.zheleznikov.articleservice.entity.Article;
import com.zheleznikov.articleservice.exception.ArticleServiceException;
import com.zheleznikov.articleservice.repository.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class ArticleServiceTest {

    private ArticleRepository articleRepository;

    private ArticleService articleService;


    @BeforeEach
    public void setup() {
        articleRepository = mock(ArticleRepository.class);
        articleService = new ArticleService(articleRepository);
        ReflectionTestUtils.setField(articleService, "message", "Article successfully created");
    }

    @Test
    public void testShouldSaveArticleCorrectly() {
        String expectedMessage = "Article successfully created";

        CreateArticleRequest request = new CreateArticleRequest()
                .setAuthor("author")
                .setContent("content")
                .setTitle("title")
                .setPublishingDate(LocalDate.now());

        CreateArticleResponse article = articleService.createArticle(request);

        Mockito.verify(articleRepository, times(1)).save(any());

        assertEquals(expectedMessage, article.getMessage());
    }

    @Test
    public void testShouldHandleExceptionWhenSaveArticle() {
        CreateArticleRequest request = new CreateArticleRequest()
                .setAuthor("author")
                .setContent("content")
                .setTitle("title")
                .setPublishingDate(LocalDate.now());

        Mockito.when(articleRepository.save(any())).thenThrow(new RuntimeException());

        assertThrows(ArticleServiceException.class, () -> articleService.createArticle(request));
    }

    @Test
    public void testShouldGetListOfArticles() {
        Pageable pageable = PageRequest.of(0, 2, Sort.by(Sort.Order.desc("createdAt")));

        List<Article> articles = List.of(
                new Article().setArticleId(UUID.randomUUID()).setContent("content").setTitle("Title")
                        .setAuthor("Author").setPublishingDate(LocalDate.now()),
                new Article().setArticleId(UUID.randomUUID()).setContent("content").setTitle("Title")
                        .setAuthor("Author").setPublishingDate(LocalDate.now())
        );


        when(articleRepository.findAll(pageable)).thenReturn(new PageImpl<>(articles));
        Page<Article> result = articleService.getArticles(0, 2);

        assertEquals(articles, result.getContent());

    }

    @Test
    public void testShouldHandleExceptionWhenGettingListOfArticles() {
        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Order.desc("createdAt")));

        Mockito.when(articleRepository.findAll(pageable)).thenThrow(new RuntimeException());
        assertThrows(ArticleServiceException.class, () -> articleService.getArticles(0,3));
    }

    @Test
    public void testShouldGetNumberOfArticlesCorrectly() {
        when(articleRepository.countBy()).thenReturn(10L);
        assertEquals(10, articleService.getNumberOfArticlesPublishedOnDailyBasesFor7Days());
    }

    @Test
    public void testShouldHandleExceptionCorrectlyWhenGetCountOfArtciles() {
        when(articleRepository.countBy()).thenThrow(new RuntimeException());
        assertThrows(ArticleServiceException.class, () -> articleService.getNumberOfArticlesPublishedOnDailyBasesFor7Days());
    }
}
