package com.whippyteam.api;

import com.whippyteam.api.manager.Manager;
import com.whippyteam.api.manager.ManagerMapper;

public interface ManagerSelector {

    Manager<?, ?> getManager(String name);

    ManagerMapper getMapper();

}
