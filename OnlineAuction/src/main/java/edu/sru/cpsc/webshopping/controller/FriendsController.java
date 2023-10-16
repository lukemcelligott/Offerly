package edu.sru.cpsc.webshopping.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.sru.cpsc.webshopping.controller.misc.Friendship;
import edu.sru.cpsc.webshopping.controller.misc.SocialFriendRequest;
import edu.sru.cpsc.webshopping.controller.misc.SocialMessage;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.repository.misc.FriendSocialRequestRepository;
import edu.sru.cpsc.webshopping.repository.user.UserRepository;
import edu.sru.cpsc.webshopping.service.FriendshipService;
import edu.sru.cpsc.webshopping.service.MessageService;
import edu.sru.cpsc.webshopping.service.UserService;


@Controller
@RequestMapping("/friends")
public class FriendsController {
    @Autowired
    private FriendshipService friendshipService;

    @Autowired
    private MessageService messageService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private FriendSocialRequestRepository friendSocialRequestRepository;

    @Autowired
    private UserService userService;
    
    @GetMapping("/addFriends")
    public String getSocialPage(Model model, Principal principal) {
    	User user = userService.getUserByUsername(principal.getName());
    	model.addAttribute("user", user);
		model.addAttribute("page", "addFriends");
       
		List<User> friends = friendshipService.getAllFriendsForUser(user);
		List<SocialMessage> messages = messageService.getAllMessagesForUser(user);
		List<SocialFriendRequest> friendRequests = friendSocialRequestRepository.findAllByReceiver(user);

        model.addAttribute("friends", friends);
        model.addAttribute("messages", messages);
        model.addAttribute("friendRequests", friendRequests);
        return "addFriends";  
    }
    
    @PostMapping({"/add"})
    public String addFriend(@RequestParam("userName") String userName, Model model, RedirectAttributes redirectAttributes, Principal principal) {
        User currentUser = userService.getUserByUsername(principal.getName());
        
        if(currentUser.getUsername().equals(userName)) {
            redirectAttributes.addFlashAttribute("errorMessage", "You cannot send a friend request to yourself!");
            return "redirect:/friends/addFriends";
        }
        
        User friendToAdd = userRepository.findByUsername(userName);

        if(friendToAdd == null) {
            System.out.println("User with username: " + userName + " not found!");
            model.addAttribute("errorMessage", "User not found!");
            return "redirect:/friends/addFriends";
        }

        if(friendshipService.sendFriendRequest(currentUser, friendToAdd)) {
            redirectAttributes.addFlashAttribute("requestSent", true);
        }

        return "redirect:/friends/addFriends";
    }
    
    @PostMapping("/remove")
    public String removeFriend(@RequestParam("friendId") Long friendId, Model model, Principal principal) {
        User currentUser = userService.getUserByUsername(principal.getName());
        Friendship friendship = friendshipService.getFriendshipBetweenUsers(currentUser, friendId);
        if(friendship != null) {
            friendshipService.removeFriendship(friendship);
        } else {
            model.addAttribute("errorMessage", "Friendship record not found!");
        }
        return "redirect:/friends/addFriends";
    }
    
    
    @GetMapping("inbox")
    public String displayInboxPage(Model model, Principal principal) {
    	User user = userService.getUserByUsername(principal.getName());
    	model.addAttribute("user", user);
		model.addAttribute("page", "inbox");
       
		List<User> friends = friendshipService.getAllFriendsForUser(user);
		List<SocialMessage> messages = messageService.getAllMessagesForUser(user);


        model.addAttribute("friends", friends);
        model.addAttribute("messages", messages);
        return "inbox";
    }
    
    @GetMapping("/api/conversations/{friendId}")
    public ResponseEntity<Map<String, Object>> getConversation(@PathVariable Long friendId, Principal principal) {
        User currentUser = userService.getUserByUsername(principal.getName());
        User friend = userRepository.findById(friendId).orElse(null);

        if (friend == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Friend not found"));
        }

        List<SocialMessage> messages = messageService.getAllMessagesForUser(currentUser, friend);

        List<Map<String, Object>> messagesList = messages.stream().map(msg -> {
            Map<String, Object> map = new HashMap<>();
            map.put("sender", Map.of("username", msg.getSender().getUsername()));
            map.put("content", msg.getContent());
            map.put("timestamp", msg.getSentTimestamp().toString()); // Convert LocalDateTime to string
            return map;
        }).collect(Collectors.toList());

        Map<String, Object> responseBody = Map.of(
            "friendName", friend.getUsername(),
            "messages", messagesList
        );
        
    	System.out.println("Big Papa Pie");

        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/acceptRequest")
    public String acceptFriendRequest(@RequestParam("requestId") Long requestId) {
        SocialFriendRequest request = friendSocialRequestRepository.findById(requestId).orElse(null);
        if (request != null) {
            User sender = request.getSender();
            User receiver = request.getReceiver();
            
            Friendship friendship = new Friendship();
            friendship.setUser1(sender);
            friendship.setUser2(receiver);
            friendshipService.addFriend(friendship);

            friendSocialRequestRepository.delete(request);
        } else {
            System.err.println("Friend request with ID " + requestId + " not found.");
        }
        return "redirect:/friends/addFriends";
    }

    @PostMapping("/declineRequest")
    public String declineFriendRequest(@RequestParam("requestId") Long requestId) {
        SocialFriendRequest request = friendSocialRequestRepository.findById(requestId)
                .orElse(null);
        if (request != null) {
            friendshipService.declineRequest(request);
        } else {
        	// Log an error message
            System.err.println("Friend request with ID " + requestId + " not found!!!");
        }
        return "redirect:/friends/addFriends";
    }
    
}