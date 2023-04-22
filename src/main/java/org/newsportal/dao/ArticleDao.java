package org.newsportal.dao;

import org.newsportal.dao.entity.Article;

import java.util.List;

public interface ArticleDao {
    void create(Article article);
    Article findById(Long id);
    List<Article> findByUserId(Long userId);
    List<Article> findAll();
    void updateById(Article article, Long id);
    void deleteById(Long id);
}
