package org.newsportal.dao.entity;

public class Article {
    private int id;
    private String title;
    private String content;
    private int userId;

    private Article() {
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getUserId() {
        return userId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int id;
        private String title;
        private String content;
        private int userId;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder userId(int userId) {
            this.userId = userId;
            return this;
        }

        public Article build() {
            Article article = new Article();
            article.id = this.id;
            article.title = this.title;
            article.content = this.content;
            article.userId = this.userId;
            return article;
        }
    }
}
