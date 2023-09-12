package edu.sru.cpsc.webshopping.repository.user;

import org.springframework.data.repository.CrudRepository;

import edu.sru.cpsc.webshopping.domain.user.FriendRequest;

public interface FriendRequestRepository extends CrudRepository<FriendRequest, Long> {
}
