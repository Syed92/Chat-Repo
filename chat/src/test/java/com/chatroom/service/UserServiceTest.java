package com.chatroom.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.chatroom.entity.User;
import com.chatroom.repository.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	@InjectMocks
	UserService userService;

	@Mock
	UserRepository userRepository;

	@Test
	public void testSaveUser() {
		User user = new User();
		user.setUsername("jimmy");
		user.setPassword("password");

		Mockito.when(userRepository.save(user)).thenReturn(user);

		userService.saveUser(user);
		assertNotNull(user);
	}

	@Test
	public void testGetUserById() {

		User user = new User();
		user.setId(1L);
		user.setUsername("testUser");

		when(userRepository.findById(1L)).thenReturn(Optional.of(user));

		User result = userService.getUserById(1L);

		assertEquals(user, result);
	}

	@Test
	public void testGetUserDetails() {

		User user = new User();
		user.setId(1L);
		user.setUsername("testUser");

		when(userRepository.findByUsername("testUser")).thenReturn(user);

		User result = userService.getUserDetails(user);

		assertEquals(user, result);
	}
}
