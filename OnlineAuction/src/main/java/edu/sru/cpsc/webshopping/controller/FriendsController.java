package edu.sru.cpsc.webshopping.controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.apiclub.captcha.Captcha;
import edu.sru.cpsc.webshopping.controller.billing.DirectDepositController;
import edu.sru.cpsc.webshopping.controller.billing.PaymentDetailsController;
import edu.sru.cpsc.webshopping.controller.billing.PaypalController;
import edu.sru.cpsc.webshopping.controller.billing.SellerRatingController;
import edu.sru.cpsc.webshopping.controller.misc.Friendship;
import edu.sru.cpsc.webshopping.controller.misc.SocialMessage;
import edu.sru.cpsc.webshopping.domain.billing.DirectDepositDetails;
import edu.sru.cpsc.webshopping.domain.billing.PaymentDetails;
import edu.sru.cpsc.webshopping.domain.billing.Paypal;
import edu.sru.cpsc.webshopping.domain.market.MarketListing;
import edu.sru.cpsc.webshopping.domain.user.Message;
import edu.sru.cpsc.webshopping.domain.user.SellerRating;
import edu.sru.cpsc.webshopping.domain.user.Statistics;
import edu.sru.cpsc.webshopping.domain.user.Statistics.StatsCategory;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.domain.widgets.Widget;
import edu.sru.cpsc.webshopping.repository.user.UserRepository;
import edu.sru.cpsc.webshopping.secure.CaptchaUtil;
import edu.sru.cpsc.webshopping.service.FriendshipService;
import edu.sru.cpsc.webshopping.service.MessageService;


@Controller
@RequestMapping("/friends")
public class FriendsController {
    @Autowired
    private FriendshipService friendshipService;

    @Autowired
    private MessageService messageService;
    
    @Autowired
    private UserController userController;
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/social")
    public String getSocialPage(Model model) {
    	User user = userController.getCurrently_Logged_In();
    	model.addAttribute("user", user);
		model.addAttribute("page", "social");
       
		List<User> friends = friendshipService.getAllFriendsForUser(user);
		List<SocialMessage> messages = messageService.getAllMessagesForUser(user);

        model.addAttribute("friends", friends);
        model.addAttribute("messages", messages);
        return "social";  
    }
    
    @PostMapping({"/add"})
    public String addFriend(@RequestParam("userName") String userName, Model model) {
        User currentUser = userController.getCurrently_Logged_In();
        User friendToAdd = userRepository.findByUsername(userName);
        
        if(friendToAdd == null) {
            // Log an error or add a message to the model
            System.out.println("User with username: " + userName + " not found!");
            model.addAttribute("errorMessage", "User not found!");
            return "redirect:/friends/social"; // or return to an error page
        }
        
        if(friendToAdd != null && !currentUser.equals(friendToAdd)) {
        	System.out.print("second if");
            Friendship friendship = new Friendship();
            friendship.setUser1(currentUser);
            friendship.setUser2(friendToAdd);
            friendshipService.addFriend(friendship);
        }
        
        return "redirect:/friends/social";
    }
    
    @PostMapping("/messages/send")
    public String sendMessage(@RequestParam String content, @RequestParam Long receiver, Model model) {
        User sender = userController.getCurrently_Logged_In();
        User receiverUser = userRepository.findById(receiver).orElse(null);
        
        if(receiverUser != null) {
            SocialMessage message = new SocialMessage();
            message.setSender(sender);
            message.setReceiver(receiverUser);
            message.setContent(content);
            messageService.saveMessage(message);
        }
        
        return "redirect:/friends/social";
    }
    
    @PostMapping("/remove")
    public String removeFriend(@RequestParam("friendId") Long friendId, Model model) {
        User currentUser = userController.getCurrently_Logged_In();
        Friendship friendship = friendshipService.getFriendshipBetweenUsers(currentUser, friendId);
        if(friendship != null) {
            friendshipService.removeFriendship(friendship);
        } else {
            model.addAttribute("errorMessage", "Friendship record not found!");
        }
        return "redirect:/friends/social";
    }

}