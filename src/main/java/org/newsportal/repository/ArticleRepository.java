package org.newsportal.repository;

import org.newsportal.repository.entity.Article;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {
    Optional<List<Article>> findAll();
    Optional<Article> findById(Long id);
    Optional<Article> findByTitle(String title);
    void create(Article article);
    Optional<Article> updateById(Article article, Long id);
    void deleteById(Long id);
}
