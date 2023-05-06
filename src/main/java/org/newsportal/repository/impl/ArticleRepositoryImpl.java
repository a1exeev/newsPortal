package org.newsportal.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.newsportal.repository.ArticleRepository;
import org.newsportal.repository.entity.Article;
import org.newsportal.repository.util.HibernateUtil;

import java.util.List;
import java.util.Optional;

public class ArticleRepositoryImpl implements ArticleRepository {
    private final SessionFactory sessionFactory;

    public ArticleRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<List<Article>> findAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<Article> query = session.createQuery("FROM Article", Article.class);
            return Optional.of(query.getResultList());
        }
    }

    @Override
    public Optional<Article> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(Article.class, id));
        }
    }

    @Override
    public Optional<Article> findByTitle(String title) {
        try (Session session = sessionFactory.openSession()) {
            Query<Article> query = session.createQuery("FROM Article WHERE title = :title", Article.class);
            query.setParameter("title", title);
            return query.uniqueResultOptional();
        }
    }

    @Override
    public void create(Article article) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(article);
            transaction.commit();
        }
    }

    @Override
    public Optional<Article> updateById(Article article, Long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Article existingArticle = session.get(Article.class, id);
            if (existingArticle != null) {
                existingArticle.setTitle(article.getTitle());
                existingArticle.setContent(article.getContent());
                existingArticle.setUser(article.getUser());
                session.update(existingArticle);
                transaction.commit();
                return Optional.of(existingArticle);
            }
            return Optional.empty();
        }
    }

    @Override
    public void deleteById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Article article = session.get(Article.class, id);
            if (article != null) {
                session.delete(article);
            }
            transaction.commit();
        }
    }

    public static void main(String[] args) {
        ArticleRepository articleRepository = new ArticleRepositoryImpl(HibernateUtil.getSessionFactory());
        Optional<Article> optionalArticle = articleRepository.findById(2L);
        Article article = optionalArticle.orElseThrow(() -> new RuntimeException("No such article"));
    }
}
