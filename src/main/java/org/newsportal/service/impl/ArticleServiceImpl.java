package org.newsportal.service.impl;

import org.newsportal.repository.ArticleRepository;
import org.newsportal.repository.entity.Article;
import org.newsportal.service.ArticleService;
import org.newsportal.service.mapper.ArticleMapper;
import org.newsportal.service.model.ArticleDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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
        return articleMapper.fromEntity(articleRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No article with id " + id))
        );
    }

    @Override
    public ArticleDto getByTitle(String title) {
        return articleMapper.fromEntity(articleRepository.findByTitle(title)
                .orElseThrow(() -> new NoSuchElementException("No article with title " + title))
        );
    }

    @Override
    public void add(ArticleDto articleDto) {
        articleRepository.create(articleMapper.fromDto(articleDto));
    }

    @Override
    public void changeById(Long id, ArticleDto articleDto) {
        Article article = articleMapper.fromDto(articleDto);
        articleRepository.updateById(article, id)
                .orElseThrow(() -> new NoSuchElementException("No article to update with id " + id));
    }

    @Override
    public void removeById(Long id) {
        articleRepository.deleteById(id);
    }
}
