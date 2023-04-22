package org.newsportal.service.lambda;

public interface Function<T, R> {
    R apply(T t);
}
