package edu.sru.cpsc.webshopping.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sru.cpsc.webshopping.controller.misc.Friendship;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.repository.misc.FriendshipRepository;

@Service
public class FriendshipService {
    @Autowired
    private FriendshipRepository friendshipRepository;
    
    public List<User> getAllFriendsForUser(User user) {
        List<Friendship> friendships = friendshipRepository.findByUser1OrUser2(user, user);
        List<User> friends = new ArrayList<>();

        for(Friendship friendship: friendships) {
            if(friendship.getUser1().equals(user)) {
                friends.add(friendship.getUser2());
            } else {
                friends.add(friendship.getUser1());
            }
        }
        
        return friends;
    }
    
    public void addFriend(Friendship friendship) {
        friendshipRepository.save(friendship);
    }

    //... methods to add, remove friends, etc. ...
}