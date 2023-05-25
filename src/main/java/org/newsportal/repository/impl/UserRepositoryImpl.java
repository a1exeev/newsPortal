package org.newsportal.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.newsportal.repository.UserRepository;
import org.newsportal.repository.entity.User;
import org.newsportal.repository.util.HibernateUtil;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserRepositoryImpl implements UserRepository {
    private final SessionFactory sessionFactory;

    public UserRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<List<User>> findAll() {
        try (Session session = sessionFactory.openSession()) {
            List<User> users = session.createQuery("FROM User", User.class).list();
            return Optional.ofNullable(users);
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.find(User.class, id);
            return Optional.ofNullable(user);
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.createQuery("FROM User WHERE username = :username", User.class)
                    .setParameter("username", username)
                    .uniqueResult();
            return Optional.ofNullable(user);
        }
    }

    @Override
    public Long create(User user) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Long id = (Long) session.save(user);
            transaction.commit();
            return id;
        }
    }

    @Override
    public Optional<User> updateById(User user, Long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            User existingUser = session.find(User.class, id);
            if (existingUser != null) {
                existingUser.setUsername(user.getUsername());
                existingUser.setPassword(user.getPassword());
                session.update(existingUser);
                transaction.commit();
                return Optional.of(existingUser);
            }
            return Optional.empty();
        }
    }

    @Override
    public void deleteById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = session.find(User.class, id);
            if (user != null) {
                session.delete(user);
                transaction.commit();
            }
        }
    }

    public static void main(String[] args) {
        UserRepository userRepository = new UserRepositoryImpl(HibernateUtil.getSessionFactory());

        final User user = new User();
        user.setUsername("test");
        user.setPassword("test");

        System.out.println(userRepository.create(user));
    }
}
