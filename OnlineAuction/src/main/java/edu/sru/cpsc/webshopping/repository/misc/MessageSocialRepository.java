package edu.sru.cpsc.webshopping.repository.misc;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.sru.cpsc.webshopping.domain.misc.SocialMessage;
import edu.sru.cpsc.webshopping.domain.user.User;



public interface MessageSocialRepository extends JpaRepository<SocialMessage, Long> {
		List<SocialMessage> findBySenderOrReceiver(User sender, User receiver);
		List<SocialMessage> findByReceiverAndIsReadFalse(User recipient);
		List<SocialMessage> findAllBySenderAndReceiver(User sender, User receiver);
	}