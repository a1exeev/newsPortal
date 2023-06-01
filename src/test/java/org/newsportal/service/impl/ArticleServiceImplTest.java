package org.newsportal.service.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.newsportal.repository.ArticleRepository;
import org.newsportal.repository.entity.Article;
import org.newsportal.service.mapper.ArticleMapper;
import org.newsportal.service.model.ArticleDto;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArticleServiceImplTest {
    @InjectMocks
    private ArticleServiceImpl articleService;
    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private ArticleMapper articleMapper;

    private static ArticleDto articleDto;
    private static Article article;

    @BeforeAll
    static void init() {
        articleDto = new ArticleDto();
        articleDto.setId(22L);
        articleDto.setTitle("title");
        articleDto.setContent("content");

        article = new Article();
        article.setId(22L);
        article.setTitle("title");
        article.setContent("content");
    }

    @Test
    void getAll() {
        when(articleRepository.findAll()).thenReturn(Optional.of(Collections.singletonList(article)));
        when(articleMapper.fromEntity(anyList())).thenReturn(Collections.singletonList(articleDto));

        List<ArticleDto> articleDtoListExpected = articleService.getAll();

        verify(articleRepository).findAll();
        verify(articleMapper).fromEntity(anyList());

        assertNotNull(articleDtoListExpected);
        assertEquals(1, articleDtoListExpected.size());
        assertEquals(articleDto, articleDtoListExpected.get(0));
    }

    @Test
    @DisplayName("Test for empty result from DB")
    void whenDbIsEmpty_thenThrowRuntimeException() {
        when(articleRepository.findAll()).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> articleService.getAll());
    }

    @Test
    void getById() {
        when(articleRepository.findById(anyLong())).thenReturn(Optional.of(article));
        when(articleMapper.fromEntity(any(Article.class))).thenReturn(articleDto);

        ArticleDto articleDtoExpected = articleService.getById(1L);

        verify(articleRepository).findById(1L);
        verify(articleMapper).fromEntity(article);

        assertNotNull(articleDtoExpected);
        assertEquals(articleDto, articleDtoExpected);
    }

    @Test
    void getByUsername() {
        when(articleRepository.findByTitle(anyString())).thenReturn(Optional.of(article));
        when(articleMapper.fromEntity(any(Article.class))).thenReturn(articleDto);

        ArticleDto articleDtoExpected = articleService.getByTitle("title");

        verify(articleRepository).findByTitle(anyString());
        verify(articleMapper).fromEntity(article);

        assertNotNull(articleDtoExpected);
        assertEquals(articleDto, articleDtoExpected);
    }

    @Test
    void add() {
        when(articleMapper.fromDto(any(ArticleDto.class))).thenReturn(article);

        articleService.add(articleDto);

        verify(articleMapper).fromDto(articleDto);
        verify(articleRepository).create(article);
    }

    @Test
    void changeById() {

        Article updatedArticleEntity = new Article();
        updatedArticleEntity.setId(22L);
        updatedArticleEntity.setTitle("updatedTitle");
        updatedArticleEntity.setContent("updatedContent");

        ArticleDto updatedArticleDto = new ArticleDto();
        updatedArticleDto.setId(22L);
        updatedArticleDto.setTitle("updatedTitle");
        updatedArticleDto.setContent("updatedContent");

        when(articleMapper.fromDto(articleDto)).thenReturn(article);
        when(articleRepository.updateById(article, 22L)).thenReturn(Optional.of(updatedArticleEntity));
        when(articleMapper.fromEntity(updatedArticleEntity)).thenReturn(updatedArticleDto);

        articleService.changeById(22L, articleDto);

        verify(articleRepository).updateById(article, 22L);
        verify(articleMapper).fromDto(articleDto);
        verify(articleMapper).fromEntity(updatedArticleEntity);
    }

    @Test
    void removeById() {
        Long id = 1L;

        articleService.removeById(id);

        verify(articleRepository).deleteById(id);
    }
}