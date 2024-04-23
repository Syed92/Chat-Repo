package com.chatroom.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chatroom.dto.MessageDto;
import com.chatroom.entity.Message;
import com.chatroom.entity.User;
import com.chatroom.service.MessageService;
import com.chatroom.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/messages")
@Slf4j
public class MessageController {

	@Autowired
	MessageService messageService;

	@Autowired
	UserService userService;

	@PostMapping("/send")
	public ResponseEntity<MessageDto> sendMessage(@RequestBody MessageDto messageDto,
			@RequestParam("userId") Long userId) {
		log.info("Sending message for user ID {}", userId);
		User user = userService.getUserById(userId);
		if (user == null) {
			return ResponseEntity.notFound().build();
		}

		Message message = messageService.sendMessage(messageDto, user);

		List<MessageDto> msgs = messageService.populateMessageDetails(Arrays.asList(message));

		return ResponseEntity.ok(msgs.get(0));
	}

	@GetMapping("/room/{roomId}")
	public ResponseEntity<List<MessageDto>> getMessagesByRoom(@PathVariable Long roomId) {

		log.info("saving messaged based on roomId {}", roomId);
		List<Message> messages = messageService.findMsgsByRoom(roomId);

		List<MessageDto> msgs = messageService.populateMessageDetails(messages);

		if (!msgs.isEmpty()) {
			// If messages are found, return them
			return ResponseEntity.ok(msgs);
		} else {
			// If no messages are found, return 404 Not Found
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/message/{messageId}")
	public ResponseEntity<MessageDto> getMessage(@PathVariable Long messageId) {
		log.info("getting messaged based on mesgId {}", messageId);
		Message messages = messageService.findMsg(messageId);

		List<MessageDto> msgs = messageService.populateMessageDetails(Arrays.asList(messages));

		if (!msgs.isEmpty()) {
			// If messages are found, return them
			return ResponseEntity.ok(msgs.get(0));
		} else {
			// If no messages are found, return 404 Not Found
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/user/{userId}")
	public ResponseEntity<?> deleteMessagesByUserId(@PathVariable Long userId) {
		log.info("deleting messages based on userId {}", userId);
		// Checking if user exists
		User user = userService.getUserById(userId);
		if (user == null) {
			return ResponseEntity.notFound().build();
		}

		// Deleting messages associated with the user
		messageService.deleteMessagesByUser(user);

		return ResponseEntity.noContent().build();
	}
}
