package edu.sru.cpsc.webshopping.controller;

import java.security.Principal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import edu.sru.cpsc.webshopping.controller.misc.SocialMessage;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.service.MessageService;
import edu.sru.cpsc.webshopping.service.UserService;

@Controller
public class WebSocketController {
    
    @Autowired
    private MessageService messageService;
    
    @Autowired
    private UserService userService;

    @MessageMapping("/send/message")
    @SendTo("/topic/messages")
    public SocialMessageDTO broadcastMessage(@Payload IncomingSocialMessageDTO messageDto, Principal principal) {
        User currentUser = userService.getUserByUsername(principal.getName());

        // Fetch the receiver using the receiverId from the messageDto
        User receiver = userService.getUserById(messageDto.getReceiverId());
        if (receiver == null) {
            System.out.println("Receiver is null");
        }

        SocialMessage message = new SocialMessage();
        message.setContent(messageDto.getContent());
        message.setSender(currentUser);
        message.setReceiver(receiver);
        message.setSentTimestamp(LocalDateTime.now());
        
        messageService.saveMessage(message);
        System.out.println("Duckies on lake");
        return toDTO(message);
    }

    // Convert SocialMessage entity to DTO
    private SocialMessageDTO toDTO(SocialMessage message) {
        return new SocialMessageDTO(
            message.getContent(),
            toDTO(message.getSender()),
            toDTO(message.getReceiver()),
            message.getSentTimestamp()
        );
    }

    // Convert User entity to DTO
    private UserDTO toDTO(User user) {
        return new UserDTO(user.getId(), user.getUsername());
    }

    public static class SocialMessageDTO {
        private String content;
        private UserDTO sender;
        private UserDTO receiver;
        private LocalDateTime timestamp;

        public SocialMessageDTO(String content, UserDTO sender, UserDTO receiver, LocalDateTime timestamp){
            this.setContent(content);
            this.setSender(sender);
            this.setReceiver(receiver);
            this.setTimestamp(timestamp);
        }

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public UserDTO getSender() {
			return sender;
		}

		public void setSender(UserDTO sender) {
			this.sender = sender;
		}

		public UserDTO getReceiver() {
			return receiver;
		}

		public void setReceiver(UserDTO receiver) {
			this.receiver = receiver;
		}
		
		 public LocalDateTime getTimestamp() {
	            return timestamp;
	        }

	        public void setTimestamp(LocalDateTime timestamp) {
	            this.timestamp = timestamp;
	        }

    }

    public static class UserDTO {
        private Long id;
        private String username;

        public UserDTO(Long id, String username) {
            this.setId(id);
            this.setUsername(username);
        }

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

    }

    public static class IncomingSocialMessageDTO {
        private String content;
        private Long receiverId;
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public Long getReceiverId() {
			return receiverId;
		}
		public void setReceiverId(Long receiverId) {
			this.receiverId = receiverId;
		}

    }
}


