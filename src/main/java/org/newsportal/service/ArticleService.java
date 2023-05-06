package org.newsportal.service;

import org.newsportal.repository.entity.Article;
import org.newsportal.service.model.ArticleDto;

import java.util.List;
import java.util.Optional;

public interface ArticleService {
    List<ArticleDto> getAll();

    ArticleDto getById(Long id);

    ArticleDto getByTitle(String title);

    void add(Article article);

    Optional<Article> changeById(Article article, Long id);

    void removeById(Long ArticleDto);
}