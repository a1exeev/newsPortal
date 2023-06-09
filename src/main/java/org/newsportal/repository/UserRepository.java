package org.newsportal.repository;

import org.newsportal.repository.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<List<User>> findAll();
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
    Long create(User user);
    Optional<User> updateById(User user, Long id);
    void deleteById(Long id);
}
