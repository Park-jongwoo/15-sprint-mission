package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
public class BaseEntity implements Serializable {

    private final UUID id;
    private String password;
    private final Long createdAt;
    private Long updatedAt;

    public BaseEntity() {
        this.id = UUID.randomUUID();
        this.createdAt = Instant.now().getEpochSecond();
        this.updatedAt = null;
    }

    // 데이터 변경 시 호출하여 수정 시간 갱신
    protected void touch() {
        this.updatedAt = Instant.now().getEpochSecond();
    }


}


