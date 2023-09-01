package edu.sru.cpsc.webshopping.controller;

import edu.sru.cpsc.webshopping.util.enums.MessageType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.sru.cpsc.webshopping.domain.user.Message;
import edu.sru.cpsc.webshopping.domain.user.Statistics;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.domain.user.Statistics.Category;
import edu.sru.cpsc.webshopping.repository.message.MessageRepository;



@RestController
public class MessageDomainController {
	private MessageRepository Messagerepository;
	private StatisticsDomainController statControl;
	private UserListDomainController userListController;
	private List<Message> allTrash = new ArrayList<>();	
	private List<Message> allSpam = new ArrayList<>();
	@PersistenceContext
	private EntityManager entityManager;
	
	MessageDomainController(MessageRepository Messagerepository,StatisticsDomainController statControl,UserListDomainController userListController) {
		this.Messagerepository = Messagerepository;
		this.statControl = statControl;
		this.userListController = userListController;
	}
	

	@RequestMapping("/get-Message/{id}")
	@Transactional
	public Message getMessage(@PathVariable("id") long id) {
	
		Message Message = entityManager.find(Message.class, id);
		return Message;
	}
	

	@RequestMapping({"/get-all-Messages"})
	public Iterable<Message> getAllMessages()
	{
		Iterable<Message> messages = Messagerepository.findAll();
		return messages;
	}
	

	@Transactional
	@PostMapping("/add-Message") 
	public void addMessage(@Validated Message Message, BindingResult result) {
		if (result.hasErrors()) {
			throw new IllegalArgumentException("Invalid Message");
		}
		User owner = entityManager.find(User.class, Message.getOwner().getId());
		User receiver = entityManager.find(User.class, Message.getReceiver().getId());
		
		Message.setOwner(owner);
		Statistics stats = new Statistics(Category.MESSAGESENT,1);
		System.out.println("HEEEEEEEEEYYYYYYY");
		System.out.println(userListController.getBlockedCheck(owner, receiver));
		if(!(userListController.getBlockedCheck(owner, receiver))) {
		Message.setReceiver(receiver);
		stats.setDescription(owner.getUsername() + " sent a message to: " + receiver.getUsername());
		}
		else {
		Message.setReceiver(null);
		stats.setDescription(owner.getUsername() + " sent a message to: " + receiver.getUsername() + " but it was blocked");	
		}
		
		
		statControl.addStatistics(stats);
		Messagerepository.save(Message);
	}
	@PostMapping("/add-Message-no-binding") 
	public void addMessage(@Validated Message Message) {
	
		User owner = entityManager.find(User.class, Message.getOwner().getId());
		User receiver = entityManager.find(User.class, Message.getReceiver().getId());
		
		Message.setOwner(owner);
		Statistics stats = new Statistics(Category.MESSAGESENT,1);
		System.out.println("HEEEEEEEEEYYYYYYY");
		System.out.println(userListController.getBlockedCheck(owner, receiver));
		if(!(userListController.getBlockedCheck(owner, receiver))) {
		Message.setReceiver(receiver);
		stats.setDescription(owner.getUsername() + " sent a message to: " + receiver.getUsername());
		}
		else {
		Message.setReceiver(null);
		stats.setDescription(owner.getUsername() + " sent a message to: " + receiver.getUsername() + " but it was blocked");	
		}
		
		
		
		statControl.addStatistics(stats);
		Messagerepository.save(Message);
	}
	@PostMapping("/save-Message-Ticket") 
	public void SaveMessageTicket(@Validated Message Message) {
	
		Messagerepository.save(Message);
	}
	@Transactional
	@PostMapping("/move-trash-back") 
	public void restoreTrash(@Validated Message Message,User user) {

		if(Message.getOldOwner() != null)	{
	if(Message.getOldOwner().getId() == user.getId()) {

		Message.setTrashOwner(null);
		Message.setOwner(user);
		Message.setOldOwner(null);
		Message.resetDateSentToTrashOwner();
		Message.resetExpireDateOwner();
	}}
		if(Message.getOldReceiver() != null)	{
	if(Message.getOldReceiver().getId() == user.getId()) {
		Message.setTrashOwnerReceiver(null);
		Message.setReceiver(user);
		Message.setOldReceiver(null);
		Message.resetDateSentToTrashReceiver();
		Message.resetExpireDateReceiver();
	}}
		
	Message.setMyTrash(null);
	Messagerepository.save(Message);
	return;
	}
	
	@Transactional
	@PostMapping("/move-spam-back") 
	public void restoreSpam(@Validated Message Message,User user) {
		Message.setUserFeedback("recovered");
		Message.resetDateSentToTrashOwner();
		if(Message.getOldOwner() != null)	{
	if(Message.getOldOwner().getId() == user.getId()) {

		Message.setSpamOwner(null);
		Message.setOwner(user);
		Message.setOldOwner(null);
	}}
		if(Message.getOldReceiver() != null)	{
	if(Message.getOldReceiver().getId() == user.getId()) {
		Message.setSpamOwnerReceiver(null);
		Message.setReceiver(user);
		Message.setOldReceiver(null);
	}}
		Message.setMySpam(null);
		Messagerepository.save(Message);
		return;
		}
	@Transactional
	@PostMapping("/add-Message-to-Trash") 
	public void addMessageToTrash(@Validated Message Message,User user) {
		Message.setMyTrash(user);
		if(Message.getOwner() == null) {
			Message.setTrashOwnerReceiver(user);
			Message.setDateSentToTrashReceiver();
			Message.setExpireDateReceiver();
			Message.setReceiver(null);
			Message.setOldReceiver(user);
			Messagerepository.save(Message);
		}
		if(Message.getReceiver() == null) {
			Message.setTrashOwner(user);
			Message.setDateSentToTrashOwner();
			Message.setExpireDateOwner();
			Message.setOwner(null);
			Message.setOldOwner(user);
			Messagerepository.save(Message);
			return;
		}
		if(user.getUsername().equals(Message.getOwner().getUsername()) && user.getUsername().equals(Message.getReceiver().getUsername()))
		{
			Message.setTrashOwner(user);
			//Message.setTrashOwnerReceiver(user);
			Message.setDateSentToTrashOwner();
			Message.setExpireDateOwner();
			Message.setDateSentToTrashReceiver();
			Message.setExpireDateReceiver();
			Message.setOwner(null);
			Message.setReceiver(null);
			Message.setOldOwner(user);
			Message.setOldReceiver(user);
			Messagerepository.save(Message);
			return;
		}
		if(user.getUsername().equals(Message.getOwner().getUsername()))
		{
			
			Message.setTrashOwner(user);
			Message.setDateSentToTrashOwner();
			Message.setExpireDateOwner();
			Message.setOldOwner(user);
			Message.setOwner(null);
			//)
			Messagerepository.save(Message);
			return;
		}
		else {
			Message.setTrashOwnerReceiver(user);
			Message.setDateSentToTrashReceiver();
			Message.setExpireDateReceiver();
			Message.setReceiver(null);
			Message.setOldReceiver(user);
			Messagerepository.save(Message);
			return;
		}

	}
	
	@Transactional
	@PostMapping("/add-Message-to-Spam") 
	public void addMessageToSpam(@Validated Message Message,User user,String category,String feedback) {
		Message.setMessageType(MessageType.valueOf(category.toUpperCase()));
		Message.setOffender(Message.getSender());
		Message.setDateSentToTrashOwner();
		Message.setUserFeedback(feedback);
		Message.setSpamReporter(user.getUsername());
		Message.setMySpam(user);
		if(Message.getOwner() == null) {
			Message.setSpamOwnerReceiver(user);
			Message.setReceiver(null);
			Message.setOldReceiver(user);
			Messagerepository.save(Message);
		}
		if(Message.getReceiver() == null) {
			Message.setSpamOwner(user);
			Message.setOwner(null);
			Message.setOldOwner(user);
			Messagerepository.save(Message);
			return;
		}
		if(user.getUsername().equals(Message.getOwner().getUsername()) && user.getUsername().equals(Message.getReceiver().getUsername()))
		{
			Message.setSpamOwner(user);
			//Message.setSpamOwnerReceiver(user);
			Message.setOwner(null);
			Message.setReceiver(null);
			Message.setOldOwner(user);
			Message.setOldReceiver(user);
			Messagerepository.save(Message);
			return;
		}
		if(user.getUsername().equals(Message.getOwner().getUsername()))
		{
			
			Message.setSpamOwner(user);
			Message.setOldOwner(user);
			Message.setOwner(null);
			//)
			Messagerepository.save(Message);
			return;
		}
		else {
			Message.setSpamOwnerReceiver(user);
			Message.setReceiver(null);
			Message.setOldReceiver(user);
			Messagerepository.save(Message);
			return;
		}

	}
	@Transactional
	@PostMapping("/delete-from-Trash") 
	public void deleteFromTrash(@Validated Message Message,User user) {
		Message.setMyTrash(null);
		if(Message.getTrashOwner() != null) {
		if(user.getUsername().equals(Message.getTrashOwner().getUsername())) {
			Message.setTrashOwner(null);
		}}
		if(Message.getTrashOwnerReceiver() != null) {
		if(user.getUsername().equals(Message.getTrashOwnerReceiver().getUsername())) {
			Message.setTrashOwnerReceiver(null);
		}}
		
	
			Messagerepository.save(Message);
			if(Message.getOwner() == null && Message.getReceiver() == null && Message.getTrashOwner() == null && Message.getTrashOwnerReceiver() == null ) {
				Messagerepository.delete(Message);
			}
			return;


	}
	
	@Transactional
	@PostMapping("/delete-from-Spam") 
	public void deleteFromSpam(@Validated Message Message,User user) {
		Message.setMySpam(null);
		if(Message.getSpamOwner() != null) {
		if(user.getUsername().equals(Message.getSpamOwner().getUsername())) {
			Message.setSpamOwner(null);
		}}
		if(Message.getSpamOwnerReceiver() != null) {
		if(user.getUsername().equals(Message.getSpamOwnerReceiver().getUsername())) {
			Message.setTrashOwnerReceiver(null);
		}}
	
			Messagerepository.save(Message);
			if(Message.getOwner() == null && Message.getReceiver() == null && Message.getSpamOwner() == null && Message.getSpamOwnerReceiver() == null ) {
				Messagerepository.deleteById(Message.getId());
			}
			return;


	}
	

	@Transactional
	@PostMapping("/edit-Message")
	public void editMessage(@Validated Message updatedMessage) {
		Optional<Message> dbListing = Messagerepository.findById(updatedMessage.getId());
		dbListing.get().setContent(updatedMessage.getContent());
		dbListing.get().setSender(updatedMessage.getSender());
		dbListing.get().setMsgDate();
		dbListing.get().setSubject(updatedMessage.getSubject());
		dbListing.get().setReceiverName(updatedMessage.getReceiverName());
		// Ensure that we are referring to persistent entities
		dbListing.get().setOwner(entityManager.find(User.class, updatedMessage.getOwner().getId()));
		dbListing.get().setReceiver(entityManager.find(User.class, updatedMessage.getReceiver().getId()));

		Messagerepository.save(dbListing.get());
	}
	
	@Transactional
	@GetMapping("get-users-Outbox")
	public Message[] getUserOutbox(@Validated User user) {
		return Messagerepository.findByOwner(user);
	}
	@Transactional
	@GetMapping("get-users-Messages")
	public Message[] getUserInbox(@Validated User user) {
		return Messagerepository.findByReceiver(user);
	}
	@Transactional
	@GetMapping("get-users-Trash-Owner")
	public Iterable<Message> getUserTrashOwner(@Validated User user) {
		return Messagerepository.findByTrashOwner(user);
	}
	@Transactional
	@GetMapping("get-users-Trash-Receiver")
	public Iterable<Message> getUserTrashOwnerReceiver(@Validated User user) {
		return Messagerepository.findByTrashOwnerReceiver(user);
	}
	@Transactional
	@GetMapping("get-users-Trash")
	public Message[] getUserTrash(@Validated User user) {
		getAllTrash().clear();
		Iterable<Message> allTrash = Messagerepository.findByMyTrash(user);

		allTrash.iterator().forEachRemaining(u -> getAllTrash().add(u));

		return getAllTrash().toArray(new Message[getAllTrash().size()]);
	}
	@Transactional
	@GetMapping("get-users-Spam-Owner")
	public Iterable<Message> getUserSpamOwner(@Validated User user) {
		return Messagerepository.findBySpamOwner(user);
	}
	@Transactional
	@GetMapping("get-users-Spam-Receiver")
	public Iterable<Message> getUserSpamOwnerReceiver(@Validated User user) {
		return Messagerepository.findBySpamOwnerReceiver(user);
	}
	
	@Transactional
	@GetMapping("get-users-Spam")
	public Message[] getUserSpam(@Validated User user) {
		getAllSpam().clear();
		Iterable<Message> allSpam = Messagerepository.findByMySpam(user);
		
		allSpam.iterator().forEachRemaining(u -> getAllSpam().add(u));

		return getAllSpam().toArray(new Message[getAllSpam().size()]);
	}
	

	@PostMapping("/delete-Message/{id}")
	public void deleteMessage(@PathVariable long id) {
		Messagerepository.deleteById(id);
	}


	public List<Message> getAllTrash() {
		return allTrash;
	}


	public void setAllTrash(List<Message> allTrash) {
		this.allTrash = allTrash;
	}


	public List<Message> getAllSpam() {
		return allSpam;
	}


	public void setAllSpam(List<Message> allSpam) {
		this.allSpam = allSpam;
	}
}
