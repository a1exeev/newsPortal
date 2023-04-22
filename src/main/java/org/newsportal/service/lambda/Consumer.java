package org.newsportal.service.lambda;

public interface Consumer<T> {
    void accept(T t);
}
