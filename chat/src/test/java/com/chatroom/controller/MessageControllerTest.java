package com.chatroom.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.chatroom.dto.MessageDto;
import com.chatroom.entity.Message;
import com.chatroom.entity.User;
import com.chatroom.service.MessageService;
import com.chatroom.service.UserService;

@RunWith(MockitoJUnitRunner.class)
public class MessageControllerTest {

	@InjectMocks
	private MessageController messageController;
	
	@Mock
    private MessageService messageService;
	
	@Mock
    private UserService userService;
	
	
	@Test
    public void testSendMessage() {
        // Mock data
        MessageDto messageDto = new MessageDto();
        messageDto.setContent("Hello, world!");
        Long userId = 1L;

        User user = new User();
        user.setId(userId);

        Message message = new Message();
        message.setId(1L);
        message.setContent(messageDto.getContent());

        when(userService.getUserById(userId)).thenReturn(user);
        when(messageService.sendMessage(messageDto, user)).thenReturn(message);
        when(messageService.populateMessageDetails(Arrays.asList(message))).thenReturn(Arrays.asList(messageDto));

        // Calling the method
        ResponseEntity<MessageDto> responseEntity = messageController.sendMessage(messageDto, userId);

        // Verifying the result
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(messageDto, responseEntity.getBody());
    }
	
	 @Test
	    public void testGetMessagesByRoom() {
	      
	        Long roomId = 1L;

	        Message message = new Message();
	        message.setId(1L);
	        message.setContent("Hello, world!");

	        MessageDto messageDto = new MessageDto();
	        messageDto.setContent("Hello, world!");

	        List<Message> messages = Arrays.asList(message);
	        List<MessageDto> messageDtos = Arrays.asList(messageDto);

	        when(messageService.findMsgsByRoom(roomId)).thenReturn(messages);
	        when(messageService.populateMessageDetails(messages)).thenReturn(messageDtos);

	        
	        ResponseEntity<List<MessageDto>> responseEntity = messageController.getMessagesByRoom(roomId);

	       
	        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	        assertEquals(messageDtos, responseEntity.getBody());
	    }
	 
	 @Test
	    public void testGetMessage() {
	        // Mock data
	        Long messageId = 1L;

	        Message message = new Message();
	        message.setId(messageId);
	        message.setContent("Hello, world!");

	        MessageDto messageDto = new MessageDto();
	        messageDto.setContent("Hello, world!");

	        when(messageService.findMsg(messageId)).thenReturn(message);
	        when(messageService.populateMessageDetails(Arrays.asList(message))).thenReturn(Arrays.asList(messageDto));

	        // Calling the method
	        ResponseEntity<MessageDto> responseEntity = messageController.getMessage(messageId);

	        // Verifying the result
	        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	        assertEquals(messageDto, responseEntity.getBody());
	    }
	 
	 @Test
	    public void testSendMessage_UserNotFound() {
	      
	        MessageDto messageDto = new MessageDto();
	        Long userId = 1L;

	        when(userService.getUserById(userId)).thenReturn(null);

	        
	        ResponseEntity<MessageDto> responseEntity = messageController.sendMessage(messageDto, userId);

	       
	        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	    }
	 
	 @Test
	    public void testGetMessagesByRoom_NoMessagesFound() {
	        // Mock data
	        Long roomId = 1L;

	        when(messageService.findMsgsByRoom(roomId)).thenReturn(new ArrayList<>());

	        // Calling the method
	        ResponseEntity<List<MessageDto>> responseEntity = messageController.getMessagesByRoom(roomId);

	        // Verifying the result
	        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	    }

	    // Test case for getMessage when message is not found
	    @Test
	    public void testGetMessage_MessageNotFound() {
	        // Mock data
	        Long messageId = 1L;

	        when(messageService.findMsg(messageId)).thenReturn(null);

	        // Calling the method
	        ResponseEntity<MessageDto> responseEntity = messageController.getMessage(messageId);

	        // Verifying the result
	        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	    }
	    
	    @Test
	    public void testDeleteMessagesByUserId_Success() {
	        // Mock data
	        Long userId = 1L;
	        User user = new User();
	        user.setId(userId);

	        // Mock behavior
	        when(userService.getUserById(userId)).thenReturn(user);

	        // Calling the method
	        ResponseEntity<?> responseEntity = messageController.deleteMessagesByUserId(userId);

	        // Verifying the result
	        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
	        verify(messageService).deleteMessagesByUser(user);
	    }

	    @Test
	    public void testDeleteMessagesByUserId_UserNotFound() {
	        // Mock data
	        Long userId = 1L;

	        // Mock behavior
	        when(userService.getUserById(userId)).thenReturn(null);

	        // Calling the method
	        ResponseEntity<?> responseEntity = messageController.deleteMessagesByUserId(userId);

	        // Verifying the result
	        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	    }
}

