package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class Message extends BaseEntity {

    private String content;
    private final UUID authorId;
    private final UUID channelId;


    public Message(String content, UUID authorId, UUID channelId) {

        super();
        this.content = content;
        this.authorId = authorId;
        this.channelId = channelId;

    }

    public void update(String content) {
        this.content = content;
        touch();
    }


}
