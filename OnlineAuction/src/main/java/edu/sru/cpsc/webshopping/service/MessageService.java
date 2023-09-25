package edu.sru.cpsc.webshopping.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sru.cpsc.webshopping.controller.misc.SocialMessage;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.repository.misc.MessageSocialRepository;

@Service
public class MessageService {
	
    @Autowired
    private MessageSocialRepository messageRepository;
    
    @Autowired
    private MessageSocialRepository messageSocialRepository;

    public List<SocialMessage> getAllMessagesForUser(User user) {
        return messageRepository.findBySenderOrReceiver(user, user);
    }
    
    public List<SocialMessage> getAllMessagesForUser(User user1, User user2) {
        return messageSocialRepository.getAllMessagesForUser(user1, user2);
    }

    public void sendMessage(SocialMessage message) {
        messageRepository.save(message);
    }
    
    public void saveMessage(SocialMessage message) {
        messageRepository.save(message);
    }
    //... methods to send, retrieve messages, etc. ...
}