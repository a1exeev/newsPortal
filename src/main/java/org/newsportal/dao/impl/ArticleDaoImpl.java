package org.newsportal.dao.impl;

import org.newsportal.dao.ArticleDao;
import org.newsportal.dao.entity.Article;
import org.newsportal.dao.util.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticleDaoImpl implements ArticleDao {

    private static final String CREATE_SQL = "INSERT INTO article (title, content, user_id) VALUES (?, ?, ?)";
    private static final String UPDATE_BY_ID_SQL = "UPDATE article SET title = ?, content = ?, user_id = ? WHERE id = ?";
    private static final String DELETE_BY_ID_SQL = "DELETE FROM article WHERE id = ?";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM article WHERE id = ?";
    private static final String FIND_ALL_SQL = "SELECT * FROM article";
    private static final String FIND_BY_USER_ID_SQL = "SELECT * FROM article WHERE user_id = ?";

    private final ConnectionPool connectionPool;

    public ArticleDaoImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public void create(Article article) {
        Connection connection = connectionPool.acquireConnection();

        try (PreparedStatement statement = connection.prepareStatement(CREATE_SQL,
                Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, article.getTitle());
            statement.setString(2, article.getContent());
            statement.setLong(3, article.getUserId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating article failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    article.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating article failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }

    @Override
    public Article findById(Long id) {
        Article article = null;
        Connection connection = connectionPool.acquireConnection();

        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
                statement.setLong(1, id);
                ResultSet resultSet = statement.executeQuery();
                    if (resultSet.next()) {
                        article = new Article();
                        article.setId(resultSet.getInt("id"));
                        article.setTitle(resultSet.getString("title"));
                        article.setContent(resultSet.getString("content"));
                        article.setUserId(resultSet.getInt("user_id"));
                    }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
        return article;
    }

    @Override
    public List<Article> findByUserId(Long userId) {
        List<Article> articles = new ArrayList<>();
        Connection connection = connectionPool.acquireConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(FIND_BY_USER_ID_SQL);
            statement.setLong(1, userId);

            ResultSet resultSet = statement.executeQuery();

            createAndFillArticle(articles, resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
        return articles;
    }

    @Override
    public List<Article> findAll() {
        List<Article> articles = new ArrayList<>();
        Connection connection = connectionPool.acquireConnection();

        try (PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL);
             ResultSet resultSet = statement.executeQuery()) {

            createAndFillArticle(articles, resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
        return articles;
    }

    @Override
    public void updateById(Article article, Long id) {
        Connection connection = connectionPool.acquireConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_BY_ID_SQL)) {

            statement.setString(1, article.getTitle());
            statement.setString(2, article.getContent());
            statement.setLong(3, article.getUserId());
            statement.setLong(4, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating article failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }

    @Override
    public void deleteById(Long id) {
        Connection connection = connectionPool.acquireConnection();
        try (
                PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID_SQL)) {

            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting article failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }

    private void createAndFillArticle(List<Article> articles, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            Article article = new Article();
            article.setId(resultSet.getInt("id"));
            article.setTitle(resultSet.getString("title"));
            article.setContent(resultSet.getString("content"));
            article.setUserId(resultSet.getInt("user_id"));
            articles.add(article);
        }
    }

    public static void main(String[] args) {
        var articleDaoImpl = new ArticleDaoImpl(ConnectionPool.getInstance());

        System.out.println(articleDaoImpl.findAll());

    }
}
