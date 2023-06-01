package org.newsportal.service.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.newsportal.repository.UserRepository;
import org.newsportal.repository.entity.User;
import org.newsportal.service.mapper.UserMapper;
import org.newsportal.service.model.UserDto;

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
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;

    private static UserDto userDto;
    private static User user;

    @BeforeAll
    static void init() {
        userDto = new UserDto();
        userDto.setId(21L);
        userDto.setUsername("username");
        userDto.setPassword("password");

        user = new User();
        user.setId(21L);
        user.setUsername("username");
        user.setPassword("password");
    }

    @Test
    void getAll() {
        when(userRepository.findAll()).thenReturn(Optional.of(Collections.singletonList(user)));
        when(userMapper.fromEntity(anyList())).thenReturn(Collections.singletonList(userDto));

        List<UserDto> userDtoListExpected = userService.getAll();

        verify(userRepository).findAll();
        verify(userMapper).fromEntity(anyList()); //как сделать без anyList?

        assertNotNull(userDtoListExpected);
        assertEquals(1, userDtoListExpected.size());
        assertEquals(userDto, userDtoListExpected.get(0));
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

        User updatedUserEntity = new User();
        updatedUserEntity.setId(21L);
        updatedUserEntity.setUsername("updatedPassword");
        updatedUserEntity.setPassword("updatedPassword");

        UserDto updatedUserDto = new UserDto();
        updatedUserDto.setId(21L);
        updatedUserDto.setUsername("updatedUsername");
        updatedUserDto.setPassword("updatedPassword");

        when(userMapper.fromDto(userDto)).thenReturn(user);
        when(userRepository.updateById(user, 21L)).thenReturn(Optional.of(updatedUserEntity));
        when(userMapper.fromEntity(updatedUserEntity)).thenReturn(updatedUserDto);

        userService.changeById(21L, userDto);

        verify(userRepository).updateById(user, 21L);
        verify(userMapper).fromDto(userDto);
        verify(userMapper).fromEntity(updatedUserEntity);
    }

    @Test
    void removeById() {
        Long id = 1L;

        userService.removeById(id);

        verify(userRepository).deleteById(id);
    }
}