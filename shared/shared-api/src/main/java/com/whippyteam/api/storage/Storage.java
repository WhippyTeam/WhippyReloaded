package com.whippyteam.api.storage;

import com.whippyteam.api.storage.exception.ReadException;

public interface Storage<T> {

    void load(T toLoad) throws ReadException;

    void save(T toSave);

    boolean checkIfCreated();

}
