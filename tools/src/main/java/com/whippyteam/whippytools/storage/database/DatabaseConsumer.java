package com.whippyteam.whippytools.storage.database;

import com.whippyteam.whippytools.storage.exception.TransactionException;

@FunctionalInterface
public interface DatabaseConsumer<T> {

    void accept(T t) throws TransactionException;

}