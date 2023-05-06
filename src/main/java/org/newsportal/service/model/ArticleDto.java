package org.newsportal.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto {

    private Long id;

    private String title;

    private String content;

    private UserDto userDto;
}
