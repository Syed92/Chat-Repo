package com.chatroom.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatroom.dto.MessageDto;
import com.chatroom.entity.Message;
import com.chatroom.entity.User;
import com.chatroom.repository.MessageRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MessageService {

	@Autowired
	MessageRepository messageRepository;

	@Autowired
	ChatRoomService chatRoomService;

	public Message sendMessage(MessageDto messageDto, User user) {
		Message message = new Message();
		message.setUser(user);
		message.setRoom(chatRoomService.getDefaultChatRoom());
		message.setContent(messageDto.getContent());
		message.setTimestamp(LocalDateTime.now());
		return messageRepository.save(message);
	}

	public List<MessageDto> populateMessageDetails(List<Message> msgs) {
		List<MessageDto> msgList = new ArrayList<>();

		for (Message msg : msgs) {
			MessageDto message = new MessageDto();
			message.setContent(msg.getContent());
			message.setTimeStamp(msg.getTimestamp());
			message.setUsername(msg.getUser().getUsername());
			message.setRoomId(msg.getRoom().getId());
			msgList.add(message);
		}

		return msgList;
	}

	public List<Message> findMsgsByRoom(Long roomId) {
		log.info("fetching room messages ");
		List<Message> messages = messageRepository.findByRoomId(roomId);

		return messages;
	}

	public Message findMsg(Long messageId) {

		Message message = messageRepository.findById(messageId).get();

		return message;
	}

	@Transactional
	public void deleteMessagesByUser(User user) {
		log.info("deleting messaged based on user {} ", user.getUsername());
		messageRepository.deleteByUser(user);

	}

}
