package org.newsportal.service.mapper.impl;

import org.newsportal.repository.entity.User;
import org.newsportal.service.mapper.UserMapper;
import org.newsportal.service.model.UserDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class UserMapperImpl implements UserMapper {
    @Override
    public UserDto fromEntity(User source) {
        if (source == null) {
            return null;
        }

        UserDto userDto = new UserDto();
        userDto.setId(source.getId());
        userDto.setUsername(source.getUsername());
        userDto.setPassword(source.getPassword());

        return userDto;
    }

    @Override
    public User fromDto(UserDto source) {
        if (source == null) {
            return null;
        }

        User user = new User();
        user.setId(source.getId());
        user.setUsername(source.getUsername());
        user.setPassword(source.getPassword());

        return user;
    }

    @Override
    public List<UserDto> fromEntity(List<User> source) {

        return source.stream().map(this::fromEntity).filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    public List<User> fromDto(List<UserDto> source) {
        return source.stream().map(this::fromDto).filter(Objects::nonNull).collect(Collectors.toList());
    }
}
