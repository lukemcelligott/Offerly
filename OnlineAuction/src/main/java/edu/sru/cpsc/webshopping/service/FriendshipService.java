package edu.sru.cpsc.webshopping.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sru.cpsc.webshopping.controller.misc.FriendStatus;
import edu.sru.cpsc.webshopping.controller.misc.Friendship;
import edu.sru.cpsc.webshopping.controller.misc.SocialFriendRequest;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.repository.misc.FriendSocialRequestRepository;
import edu.sru.cpsc.webshopping.repository.misc.FriendshipRepository;
import edu.sru.cpsc.webshopping.repository.user.UserRepository;

@Service
public class FriendshipService {
	
    @Autowired
    private FriendshipRepository friendshipRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private FriendSocialRequestRepository friendSocialRequestRepository;
    
    
    public List<User> getAllFriendsForUser(User user) {
        List<Friendship> friendships1 = friendshipRepository.findAllByUser1(user);
        List<Friendship> friendships2 = friendshipRepository.findAllByUser2(user);

        List<User> friends = new ArrayList<>();
        
        for(Friendship friendship : friendships1) {
            friends.add(friendship.getUser2());
        }
        
        for(Friendship friendship : friendships2) {
            friends.add(friendship.getUser1());
        }

        return friends;
    }
    
    public void addFriend(Friendship friendship) {
        friendshipRepository.save(friendship);
    }
    


    public Friendship getFriendshipBetweenUsers(User user1, Long userId2) {
        User user2 = userRepository.findById(userId2).orElse(null);
        if(user2 == null) {   
            return null;
        }
        
        // Attempt to find a friendship in one direction
        List<Friendship> friendships = friendshipRepository.findByUser1AndUser2(user1, user2);
        
        // If no friendship is found in the first direction, attempt the other direction
        if(friendships.isEmpty()) {
            friendships = friendshipRepository.findByUser2AndUser1(user1, user2);
        }

        // Return the first friendship found, or null if none exists
        return friendships.isEmpty() ? null : friendships.get(0);
    }
    
    public void removeFriendship(Friendship friendship) {
        friendshipRepository.delete(friendship);
    }
    
    public boolean sendFriendRequest(User sender, User receiver) {
        if(sender.equals(receiver)) {
            System.out.println("Cannot send a friend request to oneself.");
            return false;
        }
        
        // Check if they are already friends
        if(!friendshipRepository.findByUser1AndUser2(sender, receiver).isEmpty() || 
           !friendshipRepository.findByUser2AndUser1(sender, receiver).isEmpty()) {
            System.out.println("Users are already friends.");
            return false;
        }

        // Check if there's already a pending friend request from sender to receiver
        if(!friendSocialRequestRepository.findBySenderAndReceiverAndStatus(sender, receiver, FriendStatus.PENDING).isEmpty()) {
            System.out.println("A pending friend request from sender to receiver already exists.");
            return false;
        }
        
        SocialFriendRequest request = new SocialFriendRequest();
        request.setSender(sender);
        request.setReceiver(receiver);
        request.setStatus(FriendStatus.PENDING);
        friendSocialRequestRepository.save(request);
        return true;
    }
    
    public List<SocialFriendRequest> getPendingRequestsForUser(User user) {
        return friendSocialRequestRepository.findByReceiverAndStatus(user, FriendStatus.PENDING);
    }
    
    public void acceptRequest(SocialFriendRequest request) {
        request.setStatus(FriendStatus.ACCEPTED);
        friendSocialRequestRepository.save(request);
        
    }
    
    public void declineRequest(SocialFriendRequest request) {
        request.setStatus(FriendStatus.DECLINED);
        friendSocialRequestRepository.delete(request);
    }

}