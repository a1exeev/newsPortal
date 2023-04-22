package org.newsportal.service.lambda;

public interface BiFunction<T, U, R>{
    R apply(T t, U u);
}
