package edu.sru.cpsc.webshopping.controller;

import edu.sru.cpsc.webshopping.domain.user.Message;
import edu.sru.cpsc.webshopping.domain.user.Ticket;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.repository.ticket.TicketRepository;
import edu.sru.cpsc.webshopping.util.constants.TimeConstants;
import edu.sru.cpsc.webshopping.util.enums.MessageType;
import edu.sru.cpsc.webshopping.util.enums.TicketState;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Data
@RequiredArgsConstructor
public class TicketPageController {

  private String page;
  private final UserController userController;
  private final TicketRepository ticketRepository;
  private final EmailController emailController;

  @GetMapping("/tickets")
  public String getTicketsPage(Model model) {
    User user = userController.getCurrently_Logged_In();
    model.addAttribute("user", user);

    Iterable<Ticket> tickets = ticketRepository.findAllByCreatedBy(user);
    model.addAttribute("tickets", tickets);

    setPage("tickets");
    model.addAttribute("page", getPage());
    return "tickets";
  }

  @GetMapping("/tickets/{id}")
  public String getTicketDetailsPage(@PathVariable("id") Long id, Model model) {
    User user = userController.getCurrently_Logged_In();
    model.addAttribute("user", user);

    Ticket ticket = ticketRepository.findById(id).get();
    model.addAttribute("ticketdetail", ticket);
    model.addAttribute("message", new Message());

    setPage("ticketdetails");
    model.addAttribute("page", getPage());
    return "tickets";
  }

  @PostMapping("/reopenTicket/{id}")
  public String reopenTicket(@PathVariable("id") Long id, Model model) {
    User user = userController.getCurrently_Logged_In();
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
  public String createTicketsPage(Model model) {
    User user = userController.getCurrently_Logged_In();
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
  public String createTickets(Model model, Ticket ticket) {
    User user = userController.getCurrently_Logged_In();
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

    setPage("tickets");
    return "redirect:/tickets";
  }

  @PostMapping("/replyTicket/{id}")
  public String getTicketsPage(
      @PathVariable Long id, @ModelAttribute Message message, Model model) {
    User user = userController.getCurrently_Logged_In();
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
