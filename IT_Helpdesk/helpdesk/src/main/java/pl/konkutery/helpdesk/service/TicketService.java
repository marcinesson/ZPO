package pl.konkutery.helpdesk.service;

import pl.konkutery.helpdesk.model.Ticket;
import pl.konkutery.helpdesk.model.TicketStatus;
import pl.konkutery.helpdesk.model.User;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import pl.konkutery.helpdesk.repository.TicketRepository;
import pl.konkutery.helpdesk.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    // Client
    @Transactional
    public void createTicket(Long clientId, String description) {
        User client = userRepository.findById(clientId)
        .orElseThrow(() -> new RuntimeException("User with an id:  " + clientId + " doesn't exist."));

        Ticket newTicket = new Ticket();

        newTicket.setClient(client);
        newTicket.setDescription(description);
        newTicket.setTicketStatus(TicketStatus.NEW);

        ticketRepository.save(newTicket);
    }

    // Client
    public List<Ticket> getClientTickets(Long clientId) {
        return ticketRepository.findAllByClientId(clientId);
    }

    // Technican
    @Transactional
    public void deleteTicket(Long ticketId) {
        if (!ticketRepository.existsById(ticketId)) {
            throw new RuntimeException("Ticket with an id: " + ticketId + " doesn't exist.");
        }
        ticketRepository.deleteById(ticketId);
    }

    // Technician
    public List<Ticket> getNewTickets() {
        return ticketRepository.findAllByTicketStatus(TicketStatus.NEW);
    }

    // Technician
    @Transactional
    public void assignToMe(Long ticketId, Long technicianId) {
        User technician = userRepository.findById(technicianId)
        .orElseThrow(() -> new RuntimeException("User with an id:  " + technicianId + " doesn't exist."));

        Ticket ticket = ticketRepository.findById(ticketId)
        .orElseThrow(() -> new RuntimeException("Ticket with an id:  " + ticketId + " doesn't exist."));

        ticket.setTechnician(technician);
        ticket.setTicketStatus(TicketStatus.DIAGNOSING);

        ticketRepository.save(ticket);
    }
}
