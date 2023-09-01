package edu.sru.cpsc.webshopping.repository.user;

import org.springframework.data.repository.CrudRepository;

import edu.sru.cpsc.webshopping.domain.user.FriendRequest;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.domain.widgets.Subcategory;

public interface FriendRequestRepository extends CrudRepository<FriendRequest, Long> {
}
