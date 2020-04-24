package com.whippyteam.api.storage.database.mysql.system;

import com.whippyteam.api.storage.exception.ReadException;

@FunctionalInterface
public interface DatabaseConsumer<T> {

    void accept(T t) throws ReadException;

}