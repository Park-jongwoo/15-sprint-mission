package com.sprint.mission.discodeit.entity;


import lombok.Getter;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Getter
public class UserStatus extends BaseEntity{

    private final UUID userId;
    private Instant lastActiveAt;

    public UserStatus(UUID userId)
    {
        super();
        this.userId = userId;
        this.lastActiveAt = Instant.now();
    }

    public boolean isonLine()
    {
        if(this.lastActiveAt == null)
        {
            return false;
        }

        Instant fiveMinutesAgo = Instant.now().minus(5, ChronoUnit.MINUTES);

        // lastActiveAt이 5분 전보다 '이후'이면 온라인
        return this.lastActiveAt.isAfter(fiveMinutesAgo);
    }

}
