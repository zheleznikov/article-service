package com.zheleznikov.articleservice.repository;

import com.zheleznikov.articleservice.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface ArticleRepository extends JpaRepository<Article, UUID>,
        JpaSpecificationExecutor<Article> {

    @Override
    Page<Article> findAll(Pageable pageable);


    @Query(name = "select count(publishing_date) from article" +
            "where publishing_date" +
            "between current_date - INTERVAL '7 days' and current_date",
            nativeQuery = true)
    long countBy();
}
