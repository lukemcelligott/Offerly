package edu.sru.cpsc.webshopping.repository.misc;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.sru.cpsc.webshopping.controller.misc.SocialMessage;
import edu.sru.cpsc.webshopping.domain.user.User;



public interface MessageSocialRepository extends JpaRepository<SocialMessage, Long> {
		@Query("SELECT m FROM SocialMessage m WHERE (m.sender = ?1 AND m.receiver = ?2) OR (m.sender = ?2 AND m.receiver = ?1)")
		List<SocialMessage> getAllMessagesForUser(User user1, User user2);
		List<SocialMessage> findBySenderOrReceiver(User sender, User receiver);
	}