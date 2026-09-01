package com.sprint.mission.discodeit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

// 서비스 및 엔티티 import 추가
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;

@SpringBootApplication
public class DiscodeitApplication {

	public static void main(String[] args) {


		ConfigurableApplicationContext context = SpringApplication.run(DiscodeitApplication.class, args);

		UserService userService = context.getBean(UserService.class);
		ChannelService channelService = context.getBean(ChannelService.class);
		MessageService messageService = context.getBean(MessageService.class);

		User user = setupUser(userService);
		Channel channel = setupChannel(channelService);

		messageCreateTest(messageService, channel, user);
	}
		private static User setupUser(UserService userService) {
			return userService.create("testUser", "test@test.com", "password123");
		}

		private static Channel setupChannel(ChannelService channelService) {
			return channelService.create("general", "일반 채널");
		}

		private static void messageCreateTest(MessageService messageService, Channel channel, User user) {
			Message message = messageService.create("테스트 메시지입니다.", user.getId(), channel.getId());
			System.out.println("생성된 메시지: " + message.getContent() + " (ID: " + message.getId() + ")");
	}



}
