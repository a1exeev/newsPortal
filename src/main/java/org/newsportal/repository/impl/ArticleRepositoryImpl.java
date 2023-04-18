package org.newsportal.repository.impl;

import org.hibernate.SessionFactory;
import org.newsportal.repository.ArticleRepository;
import org.newsportal.repository.entity.Article;
import org.newsportal.repository.util.HibernateUtil;
import org.newsportal.repository.util.SupplierImpl;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class ArticleRepositoryImpl implements ArticleRepository {
    private final SessionFactory sessionFactory;

    public ArticleRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<List<Article>> findAll() {

        return Optional.empty();
    }

    @Override
    public Optional<Article> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Article> findByTitle(String title) {
        return Optional.empty();
    }

    @Override
    public void create(Article article) {

    }

    @Override
    public Optional<Article> updateById(Article article, Long id) {
        return Optional.empty();
    }

    @Override
    public void deleteById(Long id) {

    }

    public static void main(String[] args) {
        ArticleRepository articleRepository = new ArticleRepositoryImpl(HibernateUtil.getSessionFactory());
        Optional<Article> optionalArticle = articleRepository.findById(2L);
        Article article = optionalArticle.orElseThrow(() -> new RuntimeException("No such article"));
    }
}
