package com.chatroom.service;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.chatroom.entity.ChatRoom;
import com.chatroom.entity.User;
import com.chatroom.repository.ChatRoomRepository;

@RunWith(MockitoJUnitRunner.class)
public class ChatRoomServiceTest {

	@InjectMocks
	ChatRoomService chatRoomService;
	
	@Mock
	ChatRoomRepository chatRoomRepository;
	
	@Test
	public void saveChat() {
		ChatRoom defaultChatRoom = new ChatRoom();
    	defaultChatRoom.setId(111l);
				
		chatRoomService.createDefaultChatRoom();
		assertNotNull(defaultChatRoom);
	}
	
	
}


