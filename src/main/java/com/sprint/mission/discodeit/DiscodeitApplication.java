package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.dto.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

// 서비스 및 엔티티 import 추가
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;

@SpringBootApplication
public class DiscodeitApplication {

	public static void main(String[] args) {


		ConfigurableApplicationContext context = SpringApplication.run(DiscodeitApplication.class, args);

		UserService userService = context.getBean(UserService.class);
		ChannelService channelService = context.getBean(ChannelService.class);
		MessageService messageService = context.getBean(MessageService.class);

		UserResponseDto user = setupUser(userService);
		ChannelResponseDto channel = setupChannel(channelService);

		messageCreateTest(messageService, channel, user);
	}
	private static UserResponseDto setupUser(UserService userService) {
		String username = "testUser";
		String email = "test@test.com";

		try {
			UserCreateDto dto = new UserCreateDto(username, email, "password123", null);
			return userService.create(dto);
		} catch (IllegalArgumentException e) {
			// 이미 생성되어 중복 예외가 발생한 경우, 기존 유저를 조회해서 반환
			return userService.findAll().stream()
					.filter(u -> u.username().equals(username))
					.findFirst()
					.orElseThrow(() -> new IllegalStateException("기존 유저 조회 실패", e));
		}
	}

	private static ChannelResponseDto setupChannel(ChannelService channelService) {
		PublicChannelCreateDto dto = new PublicChannelCreateDto("general", "일반 채널");
		return channelService.createPublicChannel(dto);
	}
	private static void messageCreateTest(MessageService messageService, ChannelResponseDto channel, UserResponseDto user) {
		MessageCreateDto dto = new MessageCreateDto(
				"테스트 메시지입니다.",
				user.id(),
				channel.id(),
				null
		);
		Message message = messageService.create(dto);
		System.out.println("생성된 메시지: " + message.getContent() + " (ID: " + message.getId() + ")");
	}


}
