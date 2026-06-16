package pl.konkutery.helpdesk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.konkutery.helpdesk.model.Ticket;
import pl.konkutery.helpdesk.model.TicketStatus;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findAllByClientId(Long clientId);
    
    List<Ticket> findAllByTicketStatus(TicketStatus ticketStatus);
}
