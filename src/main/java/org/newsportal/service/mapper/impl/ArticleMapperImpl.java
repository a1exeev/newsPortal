package org.newsportal.service.mapper.impl;

import org.newsportal.repository.entity.Article;
import org.newsportal.service.mapper.ArticleMapper;
import org.newsportal.service.model.ArticleDto;
import org.newsportal.service.model.UserDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ArticleMapperImpl implements ArticleMapper {
    @Override
    public ArticleDto fromEntity(Article source) {
        if (source == null) return null;

        UserDto userDto = null;

        if (source.getUser() != null) {
            userDto = new UserDto();
            userDto.setId(source.getUser().getId());
            userDto.setUsername(source.getUser().getUsername());
            userDto.setPassword(source.getUser().getPassword());
        }

        final ArticleDto articleDto = new ArticleDto();
        articleDto.setId(source.getId());
        articleDto.setTitle(source.getTitle());
        articleDto.setContent(source.getContent());
        articleDto.setUserDto(userDto);

        return articleDto;
    }

    @Override
    public Article fromDto(ArticleDto source) {
        return null;
    }

    @Override
    public List<ArticleDto> fromEntity(List<Article> source) {

//        List<ArticleDto> dtoList = new ArrayList<>();
//
//        for (Article article: source) {
//            dtoList.add(fromEntity(article));
//        }
//
//        return dtoList;

        return source.stream().map(this::fromEntity).filter(Objects::nonNull).collect(Collectors.toList());

    }

    @Override
    public List<Article> fromDto(List<ArticleDto> source) {
        return null;
    }
}
