package com.chatroom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chatroom.entity.ChatRoom;
import com.chatroom.entity.Message;
import com.chatroom.entity.User;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
	List<Message> findByRoom(ChatRoom room);

	List<Message> findByRoomId(Long roomId);

	void deleteByUser(User user);
}
