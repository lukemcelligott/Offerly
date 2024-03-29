package edu.sru.cpsc.webshopping.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.sru.cpsc.webshopping.domain.market.MarketListing;
import edu.sru.cpsc.webshopping.domain.misc.Friendship;
import edu.sru.cpsc.webshopping.domain.misc.SocialFriendRequest;
import edu.sru.cpsc.webshopping.domain.misc.SocialMessage;
import edu.sru.cpsc.webshopping.domain.user.Statistics;
import edu.sru.cpsc.webshopping.domain.user.Statistics.StatsCategory;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.repository.market.MarketListingRepository;
import edu.sru.cpsc.webshopping.repository.misc.FriendSocialRequestRepository;
import edu.sru.cpsc.webshopping.repository.user.UserRepository;
import edu.sru.cpsc.webshopping.service.FriendshipService;
import edu.sru.cpsc.webshopping.service.MessageService;
import edu.sru.cpsc.webshopping.service.UserService;
import java.util.Collections;


@Controller
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
    
    @Autowired
    private MarketListingRepository marketListingRepository;
    
    @Autowired
    StatisticsDomainController statControl;
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @GetMapping("/Social")
    public String getSocialPage(Model model, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("page", "social");
       
        List<User> friends = friendshipService.getAllFriendsForUser(user);
        List<SocialMessage> messages = messageService.getAllMessagesForUser(user);
        List<SocialFriendRequest> friendRequests = friendSocialRequestRepository.findAllByReceiver(user);
        List<SocialFriendRequest> outgoingPendingRequests = friendshipService.getOutgoingPendingRequests(user);
        List<User> allUsers = userService.getAllUsers();
        
        model.addAttribute("allUsers", allUsers);
        model.addAttribute("myusers", allUsers);
        model.addAttribute("friends", friends);
        model.addAttribute("messages", messages);
        model.addAttribute("friendRequests", friendRequests);
        model.addAttribute("outgoingPendingRequests", outgoingPendingRequests);
        
        return "social";
    }
   
    @PostMapping("/add")
    public String addFriend(@RequestParam(name="value", required=false) String value,
                            @RequestParam(name="filterType", required=false, defaultValue="name") String filterType,
                            Model model,
                            RedirectAttributes redirectAttributes,
                            Principal principal) {

        User currentUser = userService.getUserByUsername(principal.getName());

        if (value != null && (
        	    ("name".equals(filterType) && currentUser.getUsername().equals(value)) || 
        	    ("email".equals(filterType) && currentUser.getEmail().equals(value))
        	)) {
        	    redirectAttributes.addFlashAttribute("errorMessage", "You cannot send a friend request to yourself!");
        	    return "redirect:/Social";
        	}

        User friendToAdd = null;

        if ("name".equals(filterType)) {
            friendToAdd = userRepository.findByUsername(value);
        } else if ("email".equals(filterType)) {
            friendToAdd = userRepository.findByEmail(value);
        }

        if (friendToAdd == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "User not found!");
            return "redirect:/Social";
        }

        if (!"ROLE_USER".equals(friendToAdd.getRole())) {
            redirectAttributes.addFlashAttribute("errorMessage", "User not found!");
            return "redirect:/Social";
        }

        if (friendshipService.sendFriendRequest(currentUser, friendToAdd)) {
        	// log event
		    StatsCategory cat = StatsCategory.FRIENDREQUESTSENT;
		    Statistics stat = new Statistics(cat, 1);
		    stat.setDescription(currentUser.getUsername() + " sent a friend request to " + friendToAdd.getUsername());
		    statControl.addStatistics(stat);
		    
            redirectAttributes.addFlashAttribute("requestSent", true);
        }

        return "redirect:/Social";
    }
  
    @PostMapping("/remove")
    public String removeFriend(@RequestParam("friendId") Long friendId, Model model, Principal principal) {
        User currentUser = userService.getUserByUsername(principal.getName());
        Friendship friendship = friendshipService.getFriendshipBetweenUsers(currentUser, friendId);
        User friend = userService.getUserById(friendId);
        
        if(friendship != null) {
        	// log event
    	    StatsCategory cat = StatsCategory.REMOVEDFRIEND;
    	    Statistics stat = new Statistics(cat, 1);
    	    stat.setDescription(currentUser.getUsername() + " removed " + friend.getUsername() + " as a friend");
    	    statControl.addStatistics(stat);
        	
            friendshipService.removeFriendship(friendship);
        } else {
            model.addAttribute("errorMessage", "Friendship record not found!");
        }
        
        return "redirect:/Social";
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
    
    @GetMapping("/viewProfile/{friendId}")
    public String viewFriendProfile(@PathVariable("friendId") Long friendId, Model model, Principal principal, @Valid @ModelAttribute MarketListing marketListing) {
        User user = userService.getUserByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("page", "viewProfile");

        User friend = userService.getUserById(friendId); 

        List<MarketListing> friendListings = marketListingRepository.findBySeller(friend); 
        model.addAttribute("listings", friendListings); 

        model.addAttribute("friend", friend);
        
        return "viewProfile";
    } 
   
    @GetMapping("/api/conversations/{friendId}")
    public ResponseEntity<Map<String, Object>> getConversation(@PathVariable Long friendId, Principal principal) {
        User currentUser = userService.getUserByUsername(principal.getName());
        User friend = userRepository.findById(friendId).orElse(null);

        if (friend == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "Friend not found"));
        }

        List<SocialMessage> messages = messageService.getAllMessagesForUser(currentUser, friend);

        List<Map<String, Object>> messagesList = messages.stream().map(msg -> {
            Map<String, Object> map = new HashMap<>();
            map.put("content", msg.getContent());
            map.put("sender", new HashMap<String, Object>() {{
                put("id", msg.getSender().getId());
                put("username", msg.getSender().getUsername());
            }}); // Adjust sender to have id and username
            map.put("receiver", new HashMap<String, Object>() {{
                put("id", msg.getReceiver().getId());
                put("username", msg.getReceiver().getUsername());
            }});
            map.put("timestamp", msg.getSentTimestamp().toString()); // Convert LocalDateTime to string
            return map;
        }).collect(Collectors.toList());

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("friendName", friend.getUsername());
        responseBody.put("messages", messagesList);

        // log event
        StatsCategory cat = StatsCategory.SOCIAL;
        Statistics stat = new Statistics(cat, 1);
        stat.setDescription(currentUser.getUsername() + " viewed conversation with " + friend.getUsername());
        statControl.addStatistics(stat);

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
            
            // log event
    	    StatsCategory cat = StatsCategory.FRIENDREQUESTACCEPTED;
    	    Statistics stat = new Statistics(cat, 1);
    	    stat.setDescription(receiver.getUsername() + " accepted a friend request from " + sender.getUsername());
    	    statControl.addStatistics(stat);
        } else {
            System.err.println("Friend request with ID " + requestId + " not found.");
        }
        
        return "redirect:/Social";
    }


    @PostMapping("/declineRequest")
    public String declineFriendRequest(@RequestParam("requestId") Long requestId) {
        SocialFriendRequest request = friendSocialRequestRepository.findById(requestId)
                .orElse(null);
        if (request != null) {
        	User sender = request.getSender();
            User receiver = request.getReceiver();
            
            friendshipService.declineRequest(request);
            
            // log event
    	    StatsCategory cat = StatsCategory.FRIENDREQUESTDECLINED;
    	    Statistics stat = new Statistics(cat, 1);
    	    stat.setDescription(receiver.getUsername() + " declined a friend request from " + sender.getUsername());
    	    statControl.addStatistics(stat);
        } else { 
            // Log an error message
            System.err.println("Friend request with ID " + requestId + " not found!!!");
        }
        return "redirect:/Social";
    }
    
    @GetMapping("/searchUser")
    @ResponseBody
    public List<String> searchUser(@RequestParam("value") String value, @RequestParam("filterType") String filterType) {
        List<User> matchedUsers;
        
        // Check the filterType and call the appropriate service method
        if ("name".equals(filterType)) {
            matchedUsers = userService.searchUsers(value, "name");
            return matchedUsers.stream().map(User::getUsername).collect(Collectors.toList());
        } else if ("email".equals(filterType)) {
            matchedUsers = userService.searchUsers(value, "email");
            return matchedUsers.stream().map(User::getEmail).collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException("Invalid filter type");
        }
    } 
    
    @PostMapping("/cancelRequest")
    public String cancelFriendRequest(@RequestParam("requestId") Long requestId, Principal principal, RedirectAttributes redirectAttributes) {
        User currentUser = userService.getUserByUsername(principal.getName());
        SocialFriendRequest request = friendSocialRequestRepository.findById(requestId).orElse(null);

        if (request != null && request.getSender().getId() == currentUser.getId()) {
            friendshipService.cancelFriendRequest(requestId);
            redirectAttributes.addFlashAttribute("successMessage", "Friend request cancelled successfully.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Unable to cancel friend request.");
        }
        return "redirect:/Social";
    }
  
}