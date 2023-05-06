package org.newsportal.service.impl;

import org.newsportal.repository.ArticleRepository;
import org.newsportal.repository.entity.Article;
import org.newsportal.service.ArticleService;
import org.newsportal.service.mapper.ArticleMapper;
import org.newsportal.service.model.ArticleDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleMapper articleMapper;

    private final ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleMapper articleMapper, ArticleRepository articleRepository) {
        this.articleMapper = articleMapper;
        this.articleRepository = articleRepository;
    }

    @Override
    public List<ArticleDto> getAll() {
        return articleMapper.fromEntity(
                articleRepository.findAll().orElseThrow(() -> new NoSuchElementException("No element in db"))
        );
    }

    @Override
    public ArticleDto getById(Long id) {
        return null;
    }

    @Override
    public ArticleDto getByTitle(String title) {
        return null;
    }

    @Override
    public void add(Article article) {

    }

    @Override
    public Optional<Article> changeById(Article article, Long id) {
        return Optional.empty();
    }

    @Override
    public void removeById(Long ArticleDto) {

    }
}
