package org.newsportal.service.lambda.impl;

import org.newsportal.service.lambda.Consumer;

public class ConsumerImpl<T> implements Consumer<T> {


    @Override
    public void accept(T t) {
        System.out.println(t.getClass().getName());
    }
}
