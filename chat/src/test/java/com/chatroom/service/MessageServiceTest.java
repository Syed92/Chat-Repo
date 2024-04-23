package com.chatroom.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.chatroom.dto.MessageDto;
import com.chatroom.entity.ChatRoom;
import com.chatroom.entity.Message;
import com.chatroom.entity.User;
import com.chatroom.repository.MessageRepository;

@RunWith(MockitoJUnitRunner.class)
public class MessageServiceTest {

	@InjectMocks
	MessageService messageService;

	@Mock
	MessageRepository messageRepository;

	@Mock
    private ChatRoomService chatRoomService;
	
	@Test
	public void findByIdTest() {

		Message message = new Message();
		message.setId(11L);
		message.setContent("hey");

		Mockito.when(messageRepository.findById(11L)).thenReturn(Optional.of(message));

		Optional<Message> result = Optional.ofNullable(messageService.findMsg(11L));

		assertNotNull(result);

		assertEquals("hey", result.get().getContent());
	}

	@Test
	public void sendMessageTest() {

		MessageDto messageDto = new MessageDto();
		messageDto.setContent("Hello, world!");

		User user = new User();
		user.setId(1L);
		user.setUsername("testUser");

		ChatRoom chatRoom = new ChatRoom();
		chatRoom.setId(1L);

		Message message = new Message();
		message.setId(1L);
		message.setUser(user);
		message.setRoom(chatRoom);
		message.setContent(messageDto.getContent());
		message.setTimestamp(LocalDateTime.now());

		Message result = messageService.sendMessage(messageDto, user);

		assertNotNull(user);

	}
	
	@Test
	public void findByIdRoomIdTest() {

		Message message = new Message();
		message.setId(11L);
		message.setContent("hey");
		
		List<Message> msglist = new ArrayList<>();
		msglist.add(message);

		
		Mockito.when(messageRepository.findByRoomId(11L)).thenReturn(msglist);

		Optional<List<Message>> result = Optional.ofNullable(messageService.findMsgsByRoom(11L));

		assertNotNull(result);

		assertEquals("hey", result.get().get(0).getContent());
	}
	
	 @Test
	    public void testPopulateMessageDetails() {
	       
	        List<Message> messages = new ArrayList<>();
	        Message message = new Message();
	        message.setContent("Hello, world!");
	        message.setTimestamp(LocalDateTime.now());
	        User user = new User();
	        user.setId(1L);
	        user.setUsername("testUser");
	        message.setUser(user);
	        ChatRoom chatRoom = new ChatRoom();
	        chatRoom.setId(1L);
	        message.setRoom(chatRoom);
	        messages.add(message);

	        
	        List<MessageDto> result = messageService.populateMessageDetails(messages);

	        
	        assertEquals(1, result.size());
	        MessageDto messageDto = result.get(0);
	        assertEquals("Hello, world!", messageDto.getContent());
	        assertEquals(user.getUsername(), messageDto.getUsername());
	        assertEquals(chatRoom.getId(), messageDto.getRoomId());
	    }
	 
	 @Test
	    public void testDeleteMessagesByUser() {
	        // Mock data
	        User user = new User();
	        user.setId(1L);
	        user.setUsername("testUser");
	        // Calling the method to be tested
	        messageService.deleteMessagesByUser(user);

	        // Verifying that the deleteByUser method of the repository is called with the correct user
	        verify(messageRepository).deleteByUser(user);
	    }
	
}
