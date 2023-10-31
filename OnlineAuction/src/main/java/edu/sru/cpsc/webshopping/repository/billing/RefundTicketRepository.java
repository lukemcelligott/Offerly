package edu.sru.cpsc.webshopping.repository.billing;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.sru.cpsc.webshopping.domain.billing.RefundTicket;

public interface RefundTicketRepository extends JpaRepository<RefundTicket, Long> {

}
