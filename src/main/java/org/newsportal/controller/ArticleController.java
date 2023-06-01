package org.newsportal.controller;

import lombok.RequiredArgsConstructor;
import org.newsportal.service.ArticleService;
import org.newsportal.service.UserService;
import org.newsportal.service.model.ArticleDto;
import org.newsportal.service.model.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news-portal")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final UserService userService;

    @GetMapping(value = "/articles", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ArticleDto>> getAllArticles() {
        return ResponseEntity.ok(articleService.getAll());
    }

    @GetMapping(value = "/articles/{articleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArticleDto> getArticleById(@PathVariable Long articleId) {
        return ResponseEntity.ok(articleService.getById(articleId));
    }

    @GetMapping(value = "/articles/{articleTitle}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArticleDto> getArticleByTitle(@PathVariable String articleTitle) {
        return ResponseEntity.ok(articleService.getByTitle(articleTitle));
    }

    @PostMapping(value = "/articles/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addArticle(@RequestBody ArticleDto articleDto) {
        articleService.add(articleDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/articles/change/{articleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> changeArticle(@PathVariable Long articleId, @RequestBody ArticleDto articleDto) {
        articleService.changeById(articleId, articleDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/articles/delete/{articleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteArticle(@PathVariable Long articleId) {
        articleService.removeById(articleId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping(value = "/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getById(userId));
    }

    @GetMapping(value = "/users/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getByUsername(username));
    }

    @PostMapping(value = "/users/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> addUser(@RequestBody UserDto userDto) {
        userService.add(userDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/users/change/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> changeUser(@PathVariable Long userId, @RequestBody UserDto userDto) {
        userService.changeById(userId, userDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/users/delete/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        articleService.removeById(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
