package com.whippyteam.api.manager.type;

import com.whippyteam.api.entity.WhippyPlayer;
import com.whippyteam.api.manager.Manager;

import java.util.Set;
import java.util.UUID;

public interface WhippyPlayerManager extends Manager<WhippyPlayer, UUID> {

    Set<WhippyPlayer> getOnlineEntities();
}
