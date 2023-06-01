package org.newsportal.service.impl;

import org.newsportal.repository.UserRepository;
import org.newsportal.repository.entity.User;
import org.newsportal.service.UserService;
import org.newsportal.service.mapper.UserMapper;
import org.newsportal.service.model.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public UserServiceImpl(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDto> getAll() {
        return userMapper.fromEntity(
                userRepository.findAll().orElseThrow(() -> new NoSuchElementException("No element in db"))
        );
    }

    @Override
    public UserDto getById(Long id) {
        return userMapper.fromEntity(userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No user with id " + id))
        );
    }

    @Override
    public UserDto getByUsername(String username) {
        return userMapper.fromEntity(userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("No user with username " + username))
        );
    }

    @Override
    public Long add(UserDto userDto) {
        return userRepository.create(userMapper.fromDto(userDto));
    }

    @Override
    public UserDto changeById(Long id, UserDto userDto) {
        User updatedUser = userRepository.updateById(userMapper.fromDto(userDto), id)
                .orElseThrow(() -> new NoSuchElementException("No user to update with id " + id));
        return userMapper.fromEntity(updatedUser);
    }

    @Override
    public void removeById(Long id) {
        userRepository.deleteById(id);
    }
}