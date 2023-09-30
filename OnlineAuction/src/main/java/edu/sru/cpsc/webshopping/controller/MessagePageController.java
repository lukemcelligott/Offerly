package edu.sru.cpsc.webshopping.controller;

import java.security.Principal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.sru.cpsc.webshopping.domain.user.Message;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.repository.user.UserRepository;
import edu.sru.cpsc.webshopping.service.UserService;

@Controller
public class MessagePageController {
	private UserController userController;
	private MessageDomainController msgcontrol;
	private EmailController emailController;
	private Message mailbox[];
	private Message tempMessage;
	private int pageNumber = 1;
	private boolean viewMessage = false;
	private static int PAGEMULTIPLIER = 20;
	private String page = "home";
	private String page2;
	private String page3;
	private String page4;
	private int[] id;

	@Autowired
	private UserService userService;

	
	public MessagePageController(UserController userController,MessageDomainController msgcontrol,EmailController emailController,UserRepository repo) {
		this.userController = userController;
		this.msgcontrol = msgcontrol;
		this.emailController = emailController;
		
	}
	
	
	
	 @RequestMapping({"/messages"})
	    public String returnMessages(Model model, Principal principal) {
			
			User user = userService.getUserByUsername(principal.getName());
		
			setViewMessage(false);
			
			setMailbox(msgcontrol.getUserTrash(user));
			for(int i = 0;i < getMailbox().length; i++) {
				if(getMailbox()[i].getDateSentToTrashOwner() != null) {
				if(LocalDateTime.now().compareTo(getMailbox()[i].getExpireDateOwner()) > 0 ) {
					msgcontrol.deleteFromTrash(getMailbox()[i],user);
				}
				}
			}
			for(int i = 0;i < getMailbox().length; i++) {
				if(getMailbox()[i].getDateSentToTrashReceiver() != null) {
				if(LocalDateTime.now().compareTo(getMailbox()[i].getExpireDateReceiver()) > 0 ) {
					msgcontrol.deleteFromTrash(getMailbox()[i],user);
				}
				}
			}
			setMailbox(msgcontrol.getUserInbox(user));
			Message[] tempBox = getMailbox();
			Message[] tempBox2 = getMailbox();
			/*setPageNumber((int) Math.floor(tempBox.length/PAGEMULTIPLIER)+1);
			
			if(tempBox2.length < getPageNumber()*PAGEMULTIPLIER) {
				for(int i = (getPageNumber()-1)*PAGEMULTIPLIER; i < tempBox2.length; i++){
					System.out.println(i-(getPageNumber()-1)*PAGEMULTIPLIER);

					tempBox[i-(getPageNumber()-1)*PAGEMULTIPLIER] = tempBox2[i];
				}
				}
				else {
					for(int i = (getPageNumber()-1)*PAGEMULTIPLIER; i < getPageNumber()*PAGEMULTIPLIER; i++){

							tempBox[i-(getPageNumber()-1)*PAGEMULTIPLIER] = tempBox2[i];
					}
							}
			
					*/
					
			setPage("messages");
			
			model.addAttribute("view",getViewMessage());
			model.addAttribute("page",page);
			model.addAttribute("user",user);
			model.addAttribute("mailbox",tempBox );
	
	        return "messages";
	 }
	
	@RequestMapping({"/outbox"})
	public String returnSent(Model model, Principal principal) {
		User user = userService.getUserByUsername(principal.getName());
		setMailbox(msgcontrol.getUserOutbox(user));
		Message[] tempBox = getMailbox();
		Message[] tempBox2 = getMailbox();

		setPage("outbox");
		setViewMessage(false);
		model.addAttribute("mailbox",tempBox );
		model.addAttribute("view",getViewMessage());
		model.addAttribute("page",page);
		model.addAttribute("user",user);

		
		return "messages";
	}
	 
	@RequestMapping({"/trashbox"})
	public String returnTrash(Model model, Principal principal) {
		User user = userService.getUserByUsername(principal.getName());
		setMailbox(msgcontrol.getUserTrash(user));
		Message[] tempBox = getMailbox();
		Message[] tempBox2 = getMailbox();

		setPage("trashBox");
		setViewMessage(false);
		model.addAttribute("mailbox",getMailbox());
		//model.addAttribute("mailbox",tempBox );
		model.addAttribute("view",getViewMessage());
		model.addAttribute("page",page);
		model.addAttribute("user",user);

		return "messages";
	}
	 
	@RequestMapping({"/spambox"})
	public String returnSpam(Model model, Principal principal) {
		User user = userService.getUserByUsername(principal.getName());
		setMailbox(msgcontrol.getUserSpam(user));
		Message[] tempBox = getMailbox();
		Message[] tempBox2 = getMailbox();

		setPage("spamBox");
		setViewMessage(false);
		model.addAttribute("mailbox",getMailbox());
		//model.addAttribute("mailbox",tempBox );
		model.addAttribute("view",getViewMessage());
		model.addAttribute("page",page);
		model.addAttribute("user",user);

		return "messages";
	}
	 
   @RequestMapping({"/sendmail"})
   public String sendMessage(@RequestParam("recipient") String name, @RequestParam("message") String content,@RequestParam("subject") String subject,Model model, Principal principal) {
		User user = userService.getUserByUsername(principal.getName());
		User receiver = userController.getUserByUsername(name);
		model.addAttribute("page",getPage());
		model.addAttribute("user",user);
		model.addAttribute("mailbox",getMailbox() );
		
		setViewMessage(false);
		model.addAttribute("view",getViewMessage());
		if(userController.getUserByUsername(name) == null) {
			setPage2("fail");
			model.addAttribute("page2",getPage2());
		if(getPage().contains("reply")) {
			setPage3("fail");
			model.addAttribute("page3",getPage3());
			model.addAttribute("msg", getTempMessage().getContent());
			model.addAttribute("sentFrom", getTempMessage().getSender());
			model.addAttribute("sentDate", getTempMessage().getMsgDate());
			model.addAttribute("sentSubject", getTempMessage().getSubject());
			setViewMessage(true);
			model.addAttribute("view",getViewMessage());
		}
			return "messages";   	
			}
		else {
			if(getPage().contains("reply")) {
				setPage3("sent");
				model.addAttribute("page3",getPage3());
				model.addAttribute("msg", getTempMessage().getContent());
				model.addAttribute("sentFrom", getTempMessage().getSender());
				model.addAttribute("sentDate", getTempMessage().getMsgDate());
				model.addAttribute("sentSubject", getTempMessage().getSubject());
				setViewMessage(true);
				model.addAttribute("view",getViewMessage());
			}
			setPage2("sent");
			model.addAttribute("page2",getPage2());
		}
		if(subject.length() == 0)
		{
			subject = "<no subject>";
		}
		if(content.length() == 0)
		{
			content = "<no content>";
		}
		Message message = new Message();
		message.setOwner(user);
		message.setSender(user.getUsername());
		message.setContent(content);
		message.setSubject(subject);
		message.setMsgDate();
		message.setReceiverName(name);
		message.setReceiver(receiver);
		msgcontrol.addMessage(message);
		if(getPage().contains("home")) {
			setMailbox(msgcontrol.getUserInbox(user));
		}
		if(getPage().contains("outbox")) {
			setMailbox(msgcontrol.getUserOutbox(user));
		}
		if(getPage().contains("trash")) {
			setMailbox(msgcontrol.getUserTrash(user));
		}
		if(getPage().contains("spam")) {
			setMailbox(msgcontrol.getUserSpam(user));
		}
		
		//setMailbox(user.)
		model.addAttribute("mailbox",getMailbox());
		model.addAttribute("page",getPage());
		model.addAttribute("user",user);

		emailController.messageEmail(receiver,user,message);
    	return "messages";
   }

   @RequestMapping({"/trash"})
	public String sendToTrash(@RequestParam("id") int[] id,Model model, Principal principal) {
		User user = userService.getUserByUsername(principal.getName());
		for(int i = 0; i < id.length; i++)
		{
			Message tempMessage = msgcontrol.getMessage(id[i]);
			msgcontrol.addMessageToTrash(tempMessage, user);
		}
		setPage("home");
		setPage4("sentToTrash");
		//setMailbox(msgcontrol.getUserTrash(user));
		Message[] tempBox = getMailbox();
		Message[] tempBox2 = getMailbox();

		//setPage("trashBox");
		setViewMessage(false);
		model.addAttribute("mailbox",getMailbox());
		//model.addAttribute("mailbox",tempBox );
		model.addAttribute("view",getViewMessage());
		model.addAttribute("page4",getPage4());
		model.addAttribute("page",getPage());
		model.addAttribute("user",user);
		return "messages";
   }
   
	@RequestMapping({"/recoverFromTrash"})
	public String recoverFromTrash(@RequestParam("id") int[] id,Model model, Principal principal) {
		User user = userService.getUserByUsername(principal.getName());
		for(int i = 0; i < id.length; i++)
		{
			
			Message tempMessage = msgcontrol.getMessage(id[i]);
			msgcontrol.restoreTrash(tempMessage, user);

		}
		
		setMailbox(msgcontrol.getUserTrash(user));
		Message[] tempBox = getMailbox();
		Message[] tempBox2 = getMailbox();

		setPage("trashBox");
		setPage4("recoverFromTrash");
		setViewMessage(false);
		model.addAttribute("mailbox",getMailbox());
		//model.addAttribute("mailbox",tempBox );
		model.addAttribute("view",getViewMessage());
		model.addAttribute("page",getPage());
		model.addAttribute("page4",getPage4());
		model.addAttribute("user",user);
		return "messages";	   
	}

   @RequestMapping({"/trashDelete"})
   public String sendToTrashDelete(@RequestParam("id") int[] id,Model model, Principal principal) {
		User user = userService.getUserByUsername(principal.getName());
		for(int i = 0; i < id.length; i++)
		{
			
			Message tempMessage = msgcontrol.getMessage(id[i]);
			msgcontrol.deleteFromTrash(tempMessage, user);

		}
		
		setMailbox(msgcontrol.getUserTrash(user));
		Message[] tempBox = getMailbox();
		Message[] tempBox2 = getMailbox();
		setPage4("deleteFromTrash");
		setPage("trashBox");
		setViewMessage(false);
		model.addAttribute("mailbox",getMailbox());
		//model.addAttribute("mailbox",tempBox );
		model.addAttribute("view",getViewMessage());
		model.addAttribute("page",getPage());
		model.addAttribute("page4",getPage4());
		model.addAttribute("user",user);
		return "messages";
   }
  
   @RequestMapping({"/recoverFromSpam"})
   public String recoverFromSpam(@RequestParam("id") int[] id,Model model, Principal principal) {
		User user = userService.getUserByUsername(principal.getName());
		for(int i = 0; i < id.length; i++)
		{
			
			Message tempMessage = msgcontrol.getMessage(id[i]);
			msgcontrol.restoreSpam(tempMessage, user);

			}
		setMailbox(msgcontrol.getUserSpam(user));
		Message[] tempBox = getMailbox();
		Message[] tempBox2 = getMailbox();

		setPage("spamBox");
		setPage4("recoverFromSpam");
		setViewMessage(false);
		model.addAttribute("mailbox",getMailbox());
		//model.addAttribute("mailbox",tempBox );
		model.addAttribute("view",getViewMessage());
		model.addAttribute("page",getPage());
		model.addAttribute("page4",getPage4());
		model.addAttribute("user",user);
		return "messages";	   
   }

   @RequestMapping({"/spamDelete"})
   public String sendToSpamDelete(@RequestParam("id") int[] id,Model model, Principal principal) {
		User user = userService.getUserByUsername(principal.getName());
		for(int i = 0; i < id.length; i++)
		{
			
			Message tempMessage = msgcontrol.getMessage(id[i]);
			msgcontrol.deleteFromSpam(tempMessage, user);

		}
		setMailbox(msgcontrol.getUserSpam(user));
		Message[] tempBox = getMailbox();
		Message[] tempBox2 = getMailbox();

		setPage("spamBox");
		setPage4("deleteFromSpam");
		setViewMessage(false);
		model.addAttribute("mailbox",getMailbox());
		//model.addAttribute("mailbox",tempBox );
		model.addAttribute("view",getViewMessage());
		model.addAttribute("page",getPage());
		model.addAttribute("page4",getPage4());
		model.addAttribute("user",user);
		return "messages";
   }
   
   @RequestMapping({"/replyButton"})
   public String replyButton(Model model, Principal principal) {
	   setPage("reply");
	   User user = userService.getUserByUsername(principal.getName());
		model.addAttribute("msg", getTempMessage().getContent());
		model.addAttribute("sentFrom", getTempMessage().getSender());
		model.addAttribute("sentDate", getTempMessage().getMsgDate());
		model.addAttribute("sentSubject", getTempMessage().getSubject());
	    model.addAttribute("view",getViewMessage());
		model.addAttribute("page",getPage());
		model.addAttribute("page2",getPage2());
		model.addAttribute("user",user);

		return "messages";
   }


   @RequestMapping({"/spam"})
   public String sendToSpam(@RequestParam("id") int[] id,Model model, Principal principal) {

		User user = userService.getUserByUsername(principal.getName());
		for(int i = 0; i < id.length; i++)
		{
			
			Message tempMessage = msgcontrol.getMessage(id[i]);
			msgcontrol.addMessageToSpam(tempMessage, user,"Spam","none");

			}
		//setMailbox(msgcontrol.getUserSpam(user));
		Message[] tempBox = getMailbox();
		Message[] tempBox2 = getMailbox();

		setPage4("sentToSpam");
		setViewMessage(false);
		model.addAttribute("mailbox",getMailbox());
		//model.addAttribute("mailbox",tempBox );
		model.addAttribute("view",getViewMessage());
		model.addAttribute("page",getPage());
		model.addAttribute("page4",getPage4());
		model.addAttribute("user",user);
		return "messages";
   }

   @GetMapping({"/openMessage/{id}"})
   public String openMessage(@PathVariable("id") int id,Model model, Principal principal) {
		User user = userService.getUserByUsername(principal.getName());
		setViewMessage(true);
		setTempMessage(msgcontrol.getMessage(id));
		model.addAttribute("view",getViewMessage());
		model.addAttribute("msg", getTempMessage().getContent());
		model.addAttribute("sentFrom", getTempMessage().getSender());
		model.addAttribute("sentDate", getTempMessage().getMsgDate());
		model.addAttribute("sentSubject", getTempMessage().getSubject());
		model.addAttribute("user", user);
       return "messages";
   }

   @GetMapping({"/closeMessage"})
   public String closeMessage(Model model) {
		setViewMessage(false);
		model.addAttribute("view",getViewMessage());
       	return "redirect:messages";
   }
	
	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public int[] getId() {
		return id;
	}

	public void setId(int[] id) {
		this.id = id;
	}

	public String getPage2() {
		return page2;
	}

	public void setPage2(String page2) {
		this.page2 = page2;
	}

	public Message[] getMailbox() {
		return mailbox;
	}
	
	public Message getMailboxSingle(int i) {
		return mailbox[i];
	}

	public void setMailbox(Message[] mailbox) {
		this.mailbox = mailbox;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Message getTempMessage() {
		return tempMessage;
	}

	public void setTempMessage(Message tempMessage) {
		this.tempMessage = tempMessage;
	}

	public boolean getViewMessage() {
		return this.viewMessage;
	}
	
	public void setViewMessage(boolean viewMessage) {
		this.viewMessage = viewMessage;
	}

	public String getPage3() {
		return page3;
	}

	public void setPage3(String page3) {
		this.page3 = page3;
	}

	public String getPage4() {
		return page4;
	}

	public void setPage4(String page4) {
		this.page4 = page4;
	}
}
