package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public class Channel extends BaseEntity {

    private String name;
    private String description;

    public Channel(String name, String description)
    {
        super();
        this.name = name;
        this.description = description;
    }

    public void update(String name, String description)
    {
        this.name = name;
        this.description = description;
        touch();
    }

}


