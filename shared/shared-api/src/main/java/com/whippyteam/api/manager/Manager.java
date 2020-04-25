package com.whippyteam.api.manager;

import java.util.Optional;
import java.util.Set;

public interface Manager<T, I> {

    Optional<T> get(String name);

    Optional<T> get(I identifier);

    void add(T entity);

    void remove(T entity);

    Set<T> entrySet();

}
