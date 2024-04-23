package com.chatroom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatroom.entity.ChatRoom;
import com.chatroom.repository.ChatRoomRepository;

@Service
public class ChatRoomService {

	private ChatRoom chatRoom;

	@Autowired
	ChatRoomRepository chatRoomRepository;

	public void createDefaultChatRoom() {

		ChatRoom defaultChatRoom = new ChatRoom();
		
		chatRoom = chatRoomRepository.save(defaultChatRoom);
	}

	public ChatRoom getDefaultChatRoom() {
		return chatRoom;
	}
}
