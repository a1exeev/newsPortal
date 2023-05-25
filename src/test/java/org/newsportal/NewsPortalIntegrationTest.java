package org.newsportal;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.newsportal.repository.ArticleRepository;
import org.newsportal.repository.UserRepository;
import org.newsportal.repository.entity.Article;
import org.newsportal.repository.entity.User;
import org.newsportal.repository.impl.ArticleRepositoryImpl;
import org.newsportal.repository.impl.UserRepositoryImpl;
import org.newsportal.service.ArticleService;
import org.newsportal.service.UserService;
import org.newsportal.service.impl.ArticleServiceImpl;
import org.newsportal.service.impl.UserServiceImpl;
import org.newsportal.service.mapper.ArticleMapper;
import org.newsportal.service.mapper.UserMapper;
import org.newsportal.service.mapper.impl.ArticleMapperImpl;
import org.newsportal.service.mapper.impl.UserMapperImpl;
import org.newsportal.service.model.UserDto;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
public class NewsPortalIntegrationTest {

    @Container
    private static final PostgreSQLContainer CONTAINER = new PostgreSQLContainer("postgres");

    private static ArticleRepository articleRepository;
    private static UserRepository userRepository;

    private static ArticleMapper articleMapper;
    private static UserMapper userMapper;

    private static ArticleService articleService;
    private static UserService userService;

    private static SessionFactory sessionFactory;
    private static Properties properties;

    private static UserDto userToCreate;

    @BeforeAll
    public static void init() {
        CONTAINER.start();

        properties = new Properties();
        properties.put("hibernate.connection.driver_class", "org.postgresql.Driver");
        properties.put("hibernate.connection.password", CONTAINER.getPassword());
        properties.put("hibernate.connection.url", CONTAINER.getJdbcUrl());
        properties.put("hibernate.connection.username", CONTAINER.getUsername());
        properties.put("show_sql", true);
        properties.put("spring.jpa.properties.hibernate.format_sql", true);
        properties.put("hibernate.hbm2ddl.auto", "create-drop");

        Configuration configuration = new Configuration();
        configuration.addProperties(properties);
        configuration.addAnnotatedClass(Article.class);
        configuration.addAnnotatedClass(User.class);
        sessionFactory = configuration.buildSessionFactory();

        articleRepository = new ArticleRepositoryImpl(sessionFactory);
        userRepository = new UserRepositoryImpl(sessionFactory);

        articleMapper = new ArticleMapperImpl();
        userMapper = new UserMapperImpl();

        articleService = new ArticleServiceImpl(articleMapper, articleRepository);

        userService = new UserServiceImpl(userMapper, userRepository);
        userToCreate = new UserDto();
        userToCreate.setUsername("user1");
        userToCreate.setPassword("password1");

    }

    @Test
    public void newsPortalIntegrationTest() {
        Long createdUserId = userService.add(userToCreate);
        UserDto createdUser = userService.getById(createdUserId);
        userToCreate.setId(createdUserId);

        assertEquals(userToCreate, createdUser);

        createdUser.setUsername("updatedUsername");
        createdUser.setPassword("updatedPassword");
        userService.changeById(createdUser.getId(), createdUser);

        UserDto updatedUser = userService.getByUsername("updatedUsername");
        assertEquals(createdUser, updatedUser);

        //на создание новости + ост действия приложения

    }

    @AfterAll
    public static void shutDown() {
        CONTAINER.stop();
    }
}
