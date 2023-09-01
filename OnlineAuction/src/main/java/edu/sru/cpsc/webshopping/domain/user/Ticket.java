package edu.sru.cpsc.webshopping.domain.user;

import edu.sru.cpsc.webshopping.util.enums.TicketState;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Data
public class Ticket {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne private User createdBy;
  private String subject;

  @Enumerated(EnumType.STRING)
  private TicketState state;

  private String createdAt;
  private String assignedAt;
  private String resolvedAt;

  private String updatedAt;

  @ManyToOne private User assignedTo;

  // Lazy Initialization Exception
  // https://stackoverflow.com/questions/23932684/hibernate-failed-to-lazily-initialize-a-collection
  @ManyToMany(
      fetch = FetchType.EAGER,
      cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
  @JoinTable(
      name = "ticket_message",
      joinColumns = @JoinColumn(name = "ticket_id"),
      inverseJoinColumns = @JoinColumn(name = "message_id"))
  @Fetch(FetchMode.SELECT)
  private List<Message> messages = new ArrayList<>();

  public void addMessage(Message message) {
    if (messages == null) {
      messages = new ArrayList<>();
    }
    messages.add(message);
  }

  public boolean isResolved() {
    return TicketState.RESOLVED.equals(state);
  }

  @Override
  public String toString() {
    return "Ticket{"
        + "id="
        + id
        + ", createdBy="
        + createdBy
        + ", subject='"
        + subject
        + '\''
        + ", state="
        + state
        + ", createdAt='"
        + createdAt
        + '\''
        + ", updatedAt='"
        + updatedAt
        + '\''
        + ", assignedTo="
        + assignedTo
        + '}';
  }
}
