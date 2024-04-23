package com.chatroom.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.chatroom.entity.User;
import com.chatroom.service.UserService;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
	
	@InjectMocks
	UserController userController;
	
	@Mock
	UserService userService;
	
	 @Test
	    public void testRegisterUser() {
	        // Mock data
	        User user = new User();
	        user.setId(1L);
	        user.setUsername("testUser");

	        when(userService.saveUser(user)).thenReturn(user);

	        // Calling the method
	        ResponseEntity<User> responseEntity = userController.registerUser(user);

	        // Verifying the result
	        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	        assertEquals(user, responseEntity.getBody());
	    }

	    @Test
	    public void testLogin_Successful() {
	        // Mock data
	        User user = new User();
	        user.setId(1L);
	        user.setUsername("testUser");
	        user.setPassword("password");

	        when(userService.getUserDetails(user)).thenReturn(user);

	        // Calling the method
	        ResponseEntity<?> responseEntity = userController.login(user);

	        // Verifying the result
	        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	        assertEquals("login succesful", responseEntity.getBody());
	    }

	    @Test
	    public void testLogin_InvalidCredentials() {
	        // Mock data
	        User user = new User();
	        user.setId(1L);
	        user.setUsername("testUser");
	        user.setPassword("invalidPassword");

	        when(userService.getUserDetails(user)).thenReturn(null);

	        // Calling the method
	        ResponseEntity<?> responseEntity = userController.login(user);

	        // Verifying the result
	        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	        assertEquals("invalid credentials", responseEntity.getBody());
	    }

}
