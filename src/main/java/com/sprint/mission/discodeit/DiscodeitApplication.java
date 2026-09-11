package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.dto.ChannelResponseDto;
import com.sprint.mission.discodeit.dto.MessageCreateDto;
import com.sprint.mission.discodeit.dto.PublicChannelCreateDto;
import com.sprint.mission.discodeit.dto.UserCreateDto;
import com.sprint.mission.discodeit.dto.UserResponseDto;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.UUID;

@SpringBootApplication
public class DiscodeitApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscodeitApplication.class, args);
	}

	@Bean
	public CommandLineRunner runVerification(
			UserService userService,
			ChannelService channelService,
			MessageService messageService
	) {
		return args -> {
			System.out.println("\n========== [기능 검증 테스트 시작] ==========");

			// 1. 유저 생성/조회 테스트
			String uniqueSuffix = UUID.randomUUID().toString().substring(0, 5);
			String testEmail = "dev_" + uniqueSuffix + "@test.com";
			String testUsername = "user_" + uniqueSuffix;

			UserResponseDto user;
			try {
				user = userService.create(new UserCreateDto(testUsername, testEmail, "password123!", null));
				System.out.printf("[✓] 1. 유저 생성 성공: id=%s, name=%s, email=%s%n", user.id(), user.username(), user.email());
			} catch (Exception e) {
				System.out.println("[!] 유저 생성 실패/중복 발생 -> 기존 유저 조회 시도: " + e.getMessage());
				user = userService.findAll().stream()
						.findFirst()
						.orElseThrow(() -> new IllegalStateException("테스트할 유저가 존재하지 않습니다."));
				System.out.printf("[✓] 1. 기존 유저 조회 성공: id=%s, name=%s%n", user.id(), user.username());
			}

			// 2. 채널 생성 테스트
			PublicChannelCreateDto channelDto = new PublicChannelCreateDto("channel-" + uniqueSuffix, "테스트용 공개 채널");
			ChannelResponseDto channel = channelService.createPublicChannel(channelDto);
			System.out.printf("[✓] 2. 채널 생성 성공: id=%s, name=%s%n", channel.id(), channel.name());

			// 3. 메시지 생성 및 조회 검증
			String messageText = "스프린트 미션 동작 확인 메시지 (" + uniqueSuffix + ")";
			MessageCreateDto messageDto = new MessageCreateDto(messageText, user.id(), channel.id(), null);
			Message createdMessage = messageService.create(messageDto);

			System.out.printf("[✓] 3. 메시지 발송 성공: id=%s, content='%s'%n", createdMessage.getId(), createdMessage.getContent());

			// 4. 데이터 정합성 체크
			// getAuthorId(), getChannelId()를 통해 ID 직접 일치 여부 확인
			boolean isContentMatched = messageText.equals(createdMessage.getContent());
			boolean isAuthorMatched = user.id().equals(createdMessage.getAuthorId());
			boolean isChannelMatched = channel.id().equals(createdMessage.getChannelId());

			if (isContentMatched && isAuthorMatched && isChannelMatched) {
				System.out.println("[SUCCESS] 모든 서비스 연동 및 데이터 매핑이 정상적으로 완료되었습니다.");
			} else {
				System.err.println("[FAIL] 데이터 불일치 발생:");
				System.err.printf("- 본문 일치: %b | 작성자 ID 일치: %b | 채널 ID 일치: %b%n",
						isContentMatched, isAuthorMatched, isChannelMatched);
			}

			System.out.println("============================================\n");
		};
	}
}