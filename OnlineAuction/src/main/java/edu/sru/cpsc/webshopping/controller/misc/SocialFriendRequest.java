package edu.sru.cpsc.webshopping.controller.misc;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import edu.sru.cpsc.webshopping.controller.misc.FriendStatus;
import edu.sru.cpsc.webshopping.domain.user.User;

@Entity
public class SocialFriendRequest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    private User sender;
    
    @ManyToOne
    private User receiver;
    
    @Enumerated(EnumType.STRING)
    private FriendStatus status; // PENDING, ACCEPTED, DECLINED
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public FriendStatus getStatus() {
        return status;
    }

    public void setStatus(FriendStatus status) {
        this.status = status;
    }
}