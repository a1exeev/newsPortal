package org.newsportal.dao;

import org.newsportal.dao.entity.User;

import java.util.List;

public interface UserDao {
    void create(User user);
    User findById(Long id);
    User findByArticleId(Long id);
    List<User> findAll();
    void updateById(User user, Long id);
    void deleteById(Long id);
    // без connection
}