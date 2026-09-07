package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;

import java.util.*;

public class JCFMessageService implements MessageService {

    private final Map<UUID, Message> data = new HashMap<>();
    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;

    // 의존성 주입 (DI)
    public JCFMessageService(UserRepository userRepository, ChannelRepository channelRepository) {
        this.userRepository = userRepository;
        this.channelRepository = channelRepository;
    }

    @Override
    public Message create(String content, UUID authorId, UUID channelId) {
        // Repository를 직접 사용하여 존재 여부 검증
        if (!userRepository.existsById(authorId)) {
            throw new NoSuchElementException("작성자(User)를 찾을 수 없습니다: " + authorId);
        }
        if (!channelRepository.existsById(channelId)) {
            throw new NoSuchElementException("채널(Channel)을 찾을 수 없습니다: " + channelId);
        }

        Message message = new Message(content, authorId, channelId);
        data.put(message.getId(), message);
        return message;
    }

    @Override
    public Optional<Message> read(UUID id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public List<Message> readAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public List<Message> readAllByChannelId(UUID channelId) {
        return data.values().stream()
                .filter(message -> message.getChannelId().equals(channelId))
                .toList();
    }

    @Override
    public Message update(UUID id, String content) {
        Message message = data.get(id);
        if (message != null) {
            message.update(content);
        }
        return message;
    }

    @Override
    public boolean delete(UUID id) {
        return data.remove(id) != null;
    }
}
