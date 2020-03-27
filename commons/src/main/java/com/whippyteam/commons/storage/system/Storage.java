package com.whippyteam.commons.storage.system;

import com.whippyteam.commons.exception.storage.ReadException;

public interface Storage<T> {

    void load(T toLoad) throws ReadException;

    void save(T toSave);

}
