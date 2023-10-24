package edu.sru.cpsc.webshopping.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
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
    
    public List<SocialMessage> getAllMessagesForUser(User user) {
        return messageRepository.findBySenderOrReceiver(user, user);
    }
    
    public List<SocialMessage> getAllMessagesForUser(User user1, User user2) {
        List<SocialMessage> user1ToUser2Messages = messageRepository.findAllBySenderAndReceiver(user1, user2);
        List<SocialMessage> user2ToUser1Messages = messageRepository.findAllBySenderAndReceiver(user2, user1);
        
        List<SocialMessage> allMessages = new ArrayList<>(user1ToUser2Messages);
        allMessages.addAll(user2ToUser1Messages);
        
        //Sort the messages by timestamp
        allMessages.sort(Comparator.comparing(SocialMessage::getSentTimestamp));
        
        return allMessages;
    }

    
    public List<SocialMessage> getUnreadMessagesForUser(User recipient) {
        return messageRepository.findByReceiverAndIsReadFalse(recipient);
    }

    public void markMessageAsRead(SocialMessage message) {
        message.setRead(true);
        message.setReadTimestamp(LocalDateTime.now());
        messageRepository.save(message);
    }

    public void markMessageAsDelivered(SocialMessage message) {
        message.setDelivered(true);
        messageRepository.save(message);
    }
    
    public SocialMessage saveMessage(SocialMessage message) {
    	System.out.println("Marble Cake");
        return messageRepository.save(message);
    }

}