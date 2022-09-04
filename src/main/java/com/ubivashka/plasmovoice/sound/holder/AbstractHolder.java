package com.ubivashka.plasmovoice.sound.holder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractHolder<T> implements Holder<T> {
    protected final List<T> list = new ArrayList<>();

    @Override
    public List<T> getList() {
        return Collections.unmodifiableList(list);
    }

    @Override
    public Holder<T> add(T object) {
        if (object == null)
            return this;
        list.add(object);
        return this;
    }

    @Override
    public Holder<T> add(int index, T object) {
        if (object == null)
            return this;
        list.add(index, object);
        return this;
    }

    @Override
    public Holder<T> remove(T object) {
        list.remove(object);
        return this;
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }
}
