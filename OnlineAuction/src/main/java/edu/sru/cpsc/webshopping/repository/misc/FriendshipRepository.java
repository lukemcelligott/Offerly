package edu.sru.cpsc.webshopping.repository.misc;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.sru.cpsc.webshopping.controller.misc.FriendStatus;
import edu.sru.cpsc.webshopping.controller.misc.Friendship;
import edu.sru.cpsc.webshopping.controller.misc.SocialFriendRequest;
import edu.sru.cpsc.webshopping.domain.user.User;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
		List<Friendship> findByUser1OrUser2(User user1, User user2);
		Friendship findByUser1AndUser2(User user1, User user2);
	    List<Friendship> findAllByUser1(User user1);
	    List<Friendship> findAllByUser2(User user2);
	    
	    List<Friendship> findByUser2AndUser1(User user1, User user2);
	    List<SocialFriendRequest> findBySenderAndReceiverAndStatus(User sender, User receiver, FriendStatus status);
	}

