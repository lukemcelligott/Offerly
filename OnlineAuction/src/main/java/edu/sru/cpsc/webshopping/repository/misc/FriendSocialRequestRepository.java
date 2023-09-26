package edu.sru.cpsc.webshopping.repository.misc;

import edu.sru.cpsc.webshopping.controller.misc.SocialFriendRequest;
import edu.sru.cpsc.webshopping.controller.misc.FriendStatus;
import edu.sru.cpsc.webshopping.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendSocialRequestRepository extends JpaRepository<SocialFriendRequest, Long> {
	List<SocialFriendRequest> findByReceiverAndStatus(User receiver, FriendStatus status);
	List<SocialFriendRequest> findAllByReceiver(User receiver);
}