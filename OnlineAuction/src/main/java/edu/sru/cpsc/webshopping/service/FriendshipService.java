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
        List<Friendship> friendships = friendshipRepository.findByUser1OrUser2(user, user);
        List<User> friends = new ArrayList<>();

        for(Friendship friendship: friendships) {
            if(friendship.getUser1().equals(user)) {
                friends.add(friendship.getUser2()); // changed from getUser1() to getUser2()
            } else {
                friends.add(friendship.getUser1()); // changed from getUser2() to getUser1()
            }
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
        Friendship friendship = friendshipRepository.findByUser1AndUser2(user1, user2);
        if(friendship == null) {
            friendship = friendshipRepository.findByUser1AndUser2(user2, user1);
        }
        return friendship;
    }
    
    public void removeFriendship(Friendship friendship) {
        friendshipRepository.delete(friendship);
    }
    
    public boolean sendFriendRequest(User sender, User receiver) {
        if(sender.equals(receiver)) {
            System.out.println("No good.");
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
        
        // Here you can also add code to create a Friendship entity
    }
    
    public void declineRequest(SocialFriendRequest request) {
        request.setStatus(FriendStatus.DECLINED);
        friendSocialRequestRepository.delete(request);
    }

    //... methods to add, remove friends, etc. ...
}