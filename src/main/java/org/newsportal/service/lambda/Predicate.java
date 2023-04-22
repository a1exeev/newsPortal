package org.newsportal.service.lambda;

public interface Predicate<T> {
    boolean test(T t);
}
