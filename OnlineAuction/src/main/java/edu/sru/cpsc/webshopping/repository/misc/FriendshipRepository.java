package edu.sru.cpsc.webshopping.repository.misc;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.sru.cpsc.webshopping.controller.misc.Friendship;
import edu.sru.cpsc.webshopping.domain.user.User;
import java.util.List;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
		List<Friendship> findByUser1OrUser2(User user1, User user2);
	}

