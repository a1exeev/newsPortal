package org.newsportal.service.mapper;

import org.newsportal.repository.entity.User;
import org.newsportal.service.model.UserDto;

import java.util.List;

public interface UserMapper {

    UserDto fromEntity(User source);
    User fromDto(UserDto source);

    List<UserDto> fromEntity(List<User> source);
    List<User> fromDto(List<UserDto> source);
}
