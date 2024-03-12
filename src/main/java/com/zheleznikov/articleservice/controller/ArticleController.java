package com.zheleznikov.articleservice.controller;

import com.zheleznikov.articleservice.dto.CountOfArticlesResponse;
import com.zheleznikov.articleservice.dto.CreateArticleRequest;
import com.zheleznikov.articleservice.dto.CreateArticleResponse;
import com.zheleznikov.articleservice.entity.Article;
import com.zheleznikov.articleservice.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping("/api/v1/article")
    public CreateArticleResponse createArticle(@RequestBody CreateArticleRequest request) {
        return articleService.createArticle(request);
    }

    @GetMapping("/api/v1/articles")
    public Page<Article> getListOfArticles(
            @RequestParam(defaultValue = "0") int number,
            @RequestParam(defaultValue = "15") int size) {
        return articleService.getArticles(number, size);
    }

    @GetMapping("/inner-api/article-count")
    public CountOfArticlesResponse getCountOfArticlesFor7Days() {
        return new CountOfArticlesResponse(articleService.getNumberOfArticlesPublishedOnDailyBasesFor7Days());
    }
}
