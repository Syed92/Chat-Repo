package com.chatroom.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.chatroom.entity.Message;
import com.chatroom.repository.MessageRepository;

@Controller
public class WebSocketController {

	@Autowired
	private MessageRepository messageRepository;

	@MessageMapping("/send/message")
	@SendTo("/topic/messages")
	public Message sendMessage(@Payload Message message) {

		message.setTimestamp(LocalDateTime.now());

		return messageRepository.save(message);

	}
}
