package org.newsportal.dao;

import org.newsportal.dao.entity.User;

import java.util.List;

public interface UserDao {
    List<User> findAll();
    User findById(Long id);
    void create(User user);
    User updateById(User user, Long id);
    void deleteById(Long id);
    User findByArticleId(Long id);
    // без connection
}