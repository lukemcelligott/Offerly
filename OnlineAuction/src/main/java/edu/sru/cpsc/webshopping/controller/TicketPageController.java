package edu.sru.cpsc.webshopping.controller;

import java.security.Principal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import edu.sru.cpsc.webshopping.domain.user.Message;
import edu.sru.cpsc.webshopping.domain.user.Statistics;
import edu.sru.cpsc.webshopping.domain.user.Statistics.StatsCategory;
import edu.sru.cpsc.webshopping.domain.user.Ticket;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.repository.ticket.TicketRepository;
import edu.sru.cpsc.webshopping.service.UserService;
import edu.sru.cpsc.webshopping.util.constants.TimeConstants;
import edu.sru.cpsc.webshopping.util.enums.MessageType;
import edu.sru.cpsc.webshopping.util.enums.TicketState;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Controller
@Data
@RequiredArgsConstructor
public class TicketPageController {

  private String page;
  private final TicketRepository ticketRepository;
  private final EmailController emailController;

  @Autowired
  private UserService userService;
  @Autowired
  private StatisticsDomainController statControl;


  @GetMapping("/tickets")
  public String getTicketsPage(Model model, Principal principal) {
    User user = userService.getUserByUsername(principal.getName());
    model.addAttribute("user", user);

    Iterable<Ticket> tickets = ticketRepository.findAllByCreatedBy(user);
    model.addAttribute("tickets", tickets);

    setPage("tickets");
    model.addAttribute("page", getPage());
    return "tickets";
  }

  @GetMapping("/tickets/{id}")
  public String getTicketDetailsPage(@PathVariable("id") Long id, Model model, Principal principal) {
    User user = userService.getUserByUsername(principal.getName());
    model.addAttribute("user", user);

    Ticket ticket = ticketRepository.findById(id).get();
    model.addAttribute("ticketdetail", ticket);
    model.addAttribute("message", new Message());

    setPage("ticketdetails");
    model.addAttribute("page", getPage());
    return "tickets";
  }

  @PostMapping("/reopenTicket/{id}")
  public String reopenTicket(@PathVariable("id") Long id, Model model, Principal principal) {
    User user = userService.getUserByUsername(principal.getName());
    model.addAttribute("user", user);

    Ticket ticket = ticketRepository.findById(id).get();
    ticket.setState(TicketState.UNANSWERED);
    ticket.setUpdatedAt(LocalDateTime.now().format(TimeConstants.DATE_TIME_FORMATTER));
    ticketRepository.save(ticket);

    emailController.updateTicketStatus(user, ticket, "reopen");

    Iterable<Ticket> tickets = ticketRepository.findAllByCreatedBy(user);
    model.addAttribute("tickets", tickets);

    setPage("tickets");
    model.addAttribute("page", getPage());
    return "redirect:/tickets";
  }

  @GetMapping("/createTickets")
  public String createTicketsPage(Model model, Principal principal) {
    User user = userService.getUserByUsername(principal.getName());
    model.addAttribute("user", user);

    Ticket ticket = new Ticket();
    ticket.addMessage(new Message());
    model.addAttribute("ticket", ticket);

    Iterable<Ticket> tickets = ticketRepository.findAllByCreatedBy(user);
    model.addAttribute("tickets", tickets);

    setPage("createTickets");
    model.addAttribute("page", getPage());
    return "tickets";
  }
  
  @PostMapping("/createTickets")
  public String createTickets(Model model, Ticket ticket, Principal principal) {
    User user = userService.getUserByUsername(principal.getName());
    model.addAttribute("user", user);
    model.addAttribute("ticket", ticket);

    ticket.setCreatedBy(user);
    ticket.setState(TicketState.UNASSIGNED);
    ticket.setCreatedAt(LocalDateTime.now().format(TimeConstants.DATE_TIME_FORMATTER));
    ticket.setUpdatedAt(ticket.getCreatedAt());
    ticket
        .getMessages()
        .forEach(
            message -> {
              message.setMessageType(MessageType.TICKET);
              message.setSender(user.getUsername());
              message.setMsgDate();
            });
    
    ticketRepository.save(ticket);

    emailController.updateTicketStatus(user, ticket, "create");
    
    // log event
    StatsCategory cat = StatsCategory.TICKETS;
    Statistics stat = new Statistics(cat, 1);
    stat.setDescription(user.getUsername() + " created ticket: " + ticket.getSubject() + " (ID: " + ticket.getId() + ")");
    statControl.addStatistics(stat);

    setPage("tickets");
    return "redirect:/tickets";
  }
  
		
  /**
  @GetMapping("/refund")
  public String showRefundPage(Model model, Model widgetModel,Model listingModel, String tempSearch) {
	  User user = userService.getUserByUsername(principal.getName());
	  model.addAttribute("user", user);
	  model.addAttribute("page", "refund");
	  widgetModel.addAttribute("widgets", widgetController.getAllWidgets());
	  listingModel.addAttribute("listings", marketController.getAllListings());
	  Iterable<Transaction> purchases =
			  transController.getUserPurchases(user);
	  listingModel.addAttribute("purchases", purchases);
	  
      return "refund";
  }
 */ 

  @PostMapping("/replyTicket/{id}")
  public String getTicketsPage(
      @PathVariable Long id, @ModelAttribute Message message, Model model, Principal principal) {
    User user = userService.getUserByUsername(principal.getName());
    model.addAttribute("user", user);

    Ticket ticket = ticketRepository.findById(id).get();
    if (ticket.getAssignedTo() != null) {
      ticket.setState(TicketState.UNANSWERED);
    } else {
      ticket.setState(TicketState.UNASSIGNED);
    }
    ticket.setUpdatedAt(LocalDateTime.now().format(TimeConstants.DATE_TIME_FORMATTER));
    message.setMessageType(MessageType.TICKET);
    message.setSender(user.getUsername());
    message.setMsgDate();
    ticket.addMessage(message);
    ticketRepository.save(ticket);

    emailController.updateTicketStatus(user, ticket, "reply");

    model.addAttribute("ticketdetail", ticket);
    model.addAttribute("message", new Message());

    setPage("ticketdetails");
    model.addAttribute("page", getPage());
    return "redirect:/tickets/" + id;
  }
}
