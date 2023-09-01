package edu.sru.cpsc.webshopping.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.sru.cpsc.webshopping.domain.user.FriendRequest;
import edu.sru.cpsc.webshopping.repository.user.FriendRequestRepository;

@RestController
public class FriendRequestController{
	private FriendRequestRepository requestRepo;
	
	FriendRequestController(FriendRequestRepository requestRepo)
	{
		this.requestRepo = requestRepo;
	}
	
	@RequestMapping("/get-all-friend-requests")
	public Iterable<FriendRequest> getAllFriendRequests()
	{
		return requestRepo.findAll();
	}
	
	
	
}


