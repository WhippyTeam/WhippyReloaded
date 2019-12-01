package pl.tymoteuszboba.whippytools.storage.database;

import pl.tymoteuszboba.whippytools.storage.exception.TransactionException;

@FunctionalInterface
public interface DatabaseConsumer<T> {

    void accept(T t) throws TransactionException;

}