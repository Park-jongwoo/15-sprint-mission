package com.sprint.mission.discodeit.repository;

import ch.qos.logback.core.status.Status;
import com.sprint.mission.discodeit.entity.UserStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserStatusRepository {

    UserStatus save(UserStatus userStatus);
    Optional<UserStatus> findById(UUID id);
    Optional<UserStatus> findUserId(UUID id);
    List<Status> findAll();
    void deletedById(UUID id);
    void deletedByUserId(UUID userId);
}
