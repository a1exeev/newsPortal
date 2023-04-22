package org.newsportal.service.lambda.impl;

import org.newsportal.service.lambda.Supplier;

public class SupplierImpl<T> implements Supplier<T> {
    private T t;

    @Override
    public T get() {
        return t;
    }
}

