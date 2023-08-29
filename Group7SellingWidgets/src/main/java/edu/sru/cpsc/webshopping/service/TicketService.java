package edu.sru.cpsc.webshopping.service;

import edu.sru.cpsc.webshopping.domain.user.Ticket;
import edu.sru.cpsc.webshopping.repository.ticket.TicketRepository;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TicketService {

  private final TicketRepository ticketRepository;

  public Iterable<Ticket> getAllTickets() {
    return ticketRepository.findAll();
  }

  public Ticket getTicketById(Long id) {
    return ticketRepository.findById(id).get();
  }

  public Optional<Ticket> findById(Long id) {
    return ticketRepository.findById(id);
  }

  public void save(Ticket ticket) {
    ticketRepository.save(ticket);
  }
}
