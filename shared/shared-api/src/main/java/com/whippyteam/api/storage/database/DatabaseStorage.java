package com.whippyteam.api.storage.database;

import com.whippyteam.commons.storage.system.Storage;

public interface DatabaseStorage<T> extends Storage<T> {

    void checkStructure();

}