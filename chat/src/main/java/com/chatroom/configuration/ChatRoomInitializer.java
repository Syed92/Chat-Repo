package com.chatroom.configuration;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.chatroom.service.ChatRoomService;

@Component
public class ChatRoomInitializer implements ApplicationListener<ContextRefreshedEvent> {

	private final ChatRoomService chatRoomService;

	public ChatRoomInitializer(ChatRoomService chatRoomService) {
		this.chatRoomService = chatRoomService;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// Create the chat room when the application is ready
		chatRoomService.createDefaultChatRoom();
	}
}
