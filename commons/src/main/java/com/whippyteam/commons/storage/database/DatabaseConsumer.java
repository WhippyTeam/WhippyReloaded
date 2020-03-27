package com.whippyteam.commons.storage.database;

import com.whippyteam.commons.exception.storage.ReadException;

@FunctionalInterface
public interface DatabaseConsumer<T> {

    void accept(T t) throws ReadException;

}