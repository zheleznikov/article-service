package com.zheleznikov.articleservice.service;

import com.zheleznikov.articleservice.dto.CreateArticleRequest;
import com.zheleznikov.articleservice.dto.CreateArticleResponse;
import com.zheleznikov.articleservice.entity.Article;
import com.zheleznikov.articleservice.exception.ArticleServiceException;
import com.zheleznikov.articleservice.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    private String message = "Article successfully created";

    private final String defaultSortProperty = "createdAt";

    public CreateArticleResponse createArticle(CreateArticleRequest request) {

        Article article = new Article()
                .setCreatedAt(LocalDateTime.now())
                .setAuthor(request.getAuthor())
                .setTitle(request.getTitle())
                .setContent(request.getContent())
                .setPublishingDate(request.getPublishingDate());

        try {
            articleRepository.save(article);

            return new CreateArticleResponse()
                    .setArticleId(article.getArticleId())
                    .setCreatedAt(article.getCreatedAt())
                    .setMessage(message);

        } catch (Exception e) {
            throw new ArticleServiceException(e.getMessage());
        }

    }

    public Page<Article> getArticles(int pageNumber, int pageSize) {
        try {
            Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Order.desc(defaultSortProperty)));

            return articleRepository.findAll(pageable);
        } catch (Exception e) {
            throw new ArticleServiceException(e.getMessage());
        }

    }

    public long getNumberOfArticlesPublishedOnDailyBasesFor7Days() {
        try {
            return articleRepository.countBy();
        } catch (Exception e) {
            throw new ArticleServiceException(e.getMessage());
        }

    }

}
