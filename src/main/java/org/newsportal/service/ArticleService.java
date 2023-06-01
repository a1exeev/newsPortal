    package org.newsportal.service;

    import org.newsportal.service.model.ArticleDto;

    import java.util.List;

    public interface ArticleService {
        List<ArticleDto> getAll();

        ArticleDto getById(Long id);

        ArticleDto getByTitle(String title);

        void add(ArticleDto articleDto);

        ArticleDto changeById(Long id, ArticleDto articleDto);

        void removeById(Long ArticleId);
    }