package org.newsportal.service;

import org.newsportal.repository.entity.Article;
import org.newsportal.service.model.ArticleDto;

import java.util.List;
import java.util.Optional;

public interface ArticleService {
    List<ArticleDto> getAll();

    ArticleDto getById(Long id);

    ArticleDto getByTitle(String title);

    void add(ArticleDto articleDto);

    void changeById(Long id, ArticleDto articleDto);

    void removeById(Long ArticleDto);
}