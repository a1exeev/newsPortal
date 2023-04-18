package org.newsportal.repository.util;

import java.util.function.Supplier;

public class SupplierImpl implements Supplier<RuntimeException> {
    @Override
    public RuntimeException get() {
        return new RuntimeException("No such article");
    }
}
