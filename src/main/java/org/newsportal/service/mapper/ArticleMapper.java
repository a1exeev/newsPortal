package org.newsportal.service.mapper;

import org.newsportal.repository.entity.Article;
import org.newsportal.service.model.ArticleDto;

import java.util.List;

public interface ArticleMapper {

    ArticleDto fromEntity(Article source);
    Article fromDto(ArticleDto source);

    List<ArticleDto> fromEntity(List<Article> source);
    List<Article> fromDto(List<ArticleDto> source);
}
