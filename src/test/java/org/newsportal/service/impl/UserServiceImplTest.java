package org.newsportal.service.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.newsportal.repository.UserRepository;
import org.newsportal.repository.entity.Article;
import org.newsportal.repository.entity.User;
import org.newsportal.service.mapper.ArticleMapper;
import org.newsportal.service.mapper.UserMapper;
import org.newsportal.service.model.ArticleDto;
import org.newsportal.service.model.UserDto;

import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;

    private static UserDto userDto;
    private static ArticleDto articleDto;

    private static User user;
    private static Article article;

    @BeforeAll
    static void init() {
        userDto = new UserDto();
        userDto.setId(21L);
        userDto.setUsername("username");
        userDto.setPassword("password");

        articleDto = new ArticleDto();
        articleDto.setId(22L);
        articleDto.setTitle("title");
        articleDto.setContent("content");
        articleDto.setUserDto(userDto);

        user = new User();
        user.setId(21L);
        user.setUsername("username");
        user.setPassword("password");

        article = new Article();
        article.setId(22L);
        article.setTitle("title");
        article.setContent("content");
        article.setUser(user);
    }

    @Test
    void getAll() {
        when(userRepository.findAll()).thenReturn(Optional.of(Collections.singletonList(user)));
        userService.getAll();
        verify(userRepository).findAll();
        verify(userMapper).fromEntity(anyList());
    }

    @Test
    @DisplayName("Test for empty result from DB")
    void whenDbIsEmpty_thenThrowRuntimeException() {
        when(userRepository.findAll()).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> userService.getAll());
    }

    @Test
    void getById() {

    }

    @Test
    void getByUsername() {
    }

    @Test
    void add() {
    }

    @Test
    void changeById() {
    }

    @Test
    void removeById() {
    }
}