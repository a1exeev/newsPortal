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
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


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
        when(userMapper.fromEntity(anyList())).thenReturn(Collections.singletonList(userDto));

        List <UserDto> userDtosListExpected = userService.getAll();

        verify(userRepository).findAll();
        verify(userMapper).fromEntity(anyList()); //как сделать без anyList?

        assertNotNull(userDtosListExpected);
        assertEquals(1, userDtosListExpected.size());
        assertEquals(userDto, userDtosListExpected.get(0));
    }

    @Test
    @DisplayName("Test for empty result from DB")
    void whenDbIsEmpty_thenThrowRuntimeException() {
        when(userRepository.findAll()).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> userService.getAll());
    }

    @Test
    void getById() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userMapper.fromEntity(any(User.class))).thenReturn(userDto);

        UserDto userDtoExpected = userService.getById(1L);

        verify(userRepository).findById(1L);
        verify(userMapper).fromEntity(user);

        assertNotNull(userDtoExpected);
        assertEquals(userDto, userDtoExpected);
    }

    @Test
    void getByUsername() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(userMapper.fromEntity(any(User.class))).thenReturn(userDto);

        UserDto userDtoExpected = userService.getByUsername("username");

        verify(userRepository).findByUsername(anyString());
        verify(userMapper).fromEntity(user);

        assertNotNull(userDtoExpected);
        assertEquals(userDto, userDtoExpected);
    }


    @Test
    void add() {
        when(userMapper.fromDto(any(UserDto.class))).thenReturn(user);

        userService.add(userDto);

        verify(userMapper).fromDto(userDto);
        verify(userRepository).create(user);
    }

    @Test
    void changeById() {
        Long id = 21L;

        UserDto updatedUserDto = new UserDto();
        updatedUserDto.setId(id);
        updatedUserDto.setUsername("newUsername");
        updatedUserDto.setPassword("newPassword");

        User user = new User();
        user.setId(id);
        user.setUsername("username");
        user.setPassword("password");

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userMapper.fromDto(updatedUserDto)).thenReturn(user);
        when(userMapper.fromEntity(user)).thenReturn(updatedUserDto);

        userService.changeById(id, updatedUserDto);

        verify(userRepository).findById(id);
        verify(userRepository).updateById(user, id);
        verify(userMapper).fromDto(updatedUserDto);
        verify(userMapper).fromEntity(user);
    }

    @Test
    void removeById() {
        Long id = 1L;

        userService.removeById(id);

        verify(userRepository).deleteById(id);
    }
}