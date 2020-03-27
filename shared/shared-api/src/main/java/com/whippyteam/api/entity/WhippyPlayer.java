package com.whippyteam.api.entity;

import java.util.UUID;

public interface WhippyPlayer extends IdentifableEntity<UUID> {

    String getName();

    void setName(String name);

    void updatePlayerReference();

}
