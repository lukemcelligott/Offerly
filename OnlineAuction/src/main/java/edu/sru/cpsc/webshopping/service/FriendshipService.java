package edu.sru.cpsc.webshopping.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sru.cpsc.webshopping.controller.misc.Friendship;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.repository.user.UserRepository;
import edu.sru.cpsc.webshopping.repository.misc.FriendshipRepository;

@Service
public class FriendshipService {
	
    @Autowired
    private FriendshipRepository friendshipRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    
    public List<User> getAllFriendsForUser(User user) {
        List<Friendship> friendships = friendshipRepository.findByUser1OrUser2(user, user);
        List<User> friends = new ArrayList<>();

        for(Friendship friendship: friendships) {
            if(friendship.getUser1().equals(user)) {
                friends.add(friendship.getUser1());
            } else {
                friends.add(friendship.getUser2());
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

    //... methods to add, remove friends, etc. ...
}