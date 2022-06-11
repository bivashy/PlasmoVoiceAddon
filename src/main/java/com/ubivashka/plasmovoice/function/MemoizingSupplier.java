package com.ubivashka.plasmovoice.function;

import java.io.Serializable;
import java.util.function.Supplier;

public final class MemoizingSupplier<T> implements Supplier<T>, Serializable {

    final Supplier<T> delegate;
    transient volatile boolean initialized;
    // "value" does not need to be volatile; visibility piggy-backs
    // on volatile read of "initialized".
    transient T value;

    MemoizingSupplier(Supplier<T> delegate) {
        this.delegate = delegate;
    }

    @Override
    public T get() {
        // A 2-field variant of Double Checked Locking.
        if (!initialized) {
            synchronized (this) {
                if (!initialized) {
                    T t = delegate.get();
                    value = t;
                    initialized = true;
                    return t;
                }
            }
        }
        return value;
    }

    @Override
    public String toString() {
        return "Suppliers.memoize(" + delegate + ")";
    }

    private static final long serialVersionUID = 0;

    public static <T> Supplier<T> memoize(Supplier<T> supplier) {
        return new MemoizingSupplier<>(supplier);
    }

}