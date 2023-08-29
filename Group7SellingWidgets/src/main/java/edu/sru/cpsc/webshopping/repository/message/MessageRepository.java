package edu.sru.cpsc.webshopping.repository.message;

import org.springframework.data.repository.CrudRepository;

import edu.sru.cpsc.webshopping.domain.user.Message;
import edu.sru.cpsc.webshopping.domain.user.User;

public interface MessageRepository extends CrudRepository<Message, Long> {
	Message[] findByOwner(User user);
	Message[] findByReceiver(User user);
	Iterable<Message> findByTrashOwner(User user);
	Iterable<Message> findByTrashOwnerReceiver(User user);
	Iterable<Message> findBySpamOwner(User user);
	Iterable<Message> findBySpamOwnerReceiver(User user);
	Iterable<Message> findByMySpam(User user);
	Iterable<Message> findByMyTrash(User user);
}
