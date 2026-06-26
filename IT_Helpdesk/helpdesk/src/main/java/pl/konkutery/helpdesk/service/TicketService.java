package pl.konkutery.helpdesk.service;

import pl.konkutery.helpdesk.model.ServiceTask;
import pl.konkutery.helpdesk.model.Ticket;
import pl.konkutery.helpdesk.model.TicketStatus;
import pl.konkutery.helpdesk.model.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import pl.konkutery.helpdesk.repository.ServiceTaskRepository;
import pl.konkutery.helpdesk.repository.TicketRepository;
import pl.konkutery.helpdesk.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final ServiceTaskRepository serviceTaskRepository;

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

    // Technician
    @Transactional
    public void updateStatus(Long ticketId, TicketStatus ticketStatus) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket with an id:  " + ticketId + " doesn't exist."));

        ticket.setTicketStatus(ticketStatus);

        ticketRepository.save(ticket);
    }

    // Technician
    @Transactional
    public void addServiceTasktoTicket(Long ticketId, Long serviceTaskId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket with an id:  " + ticketId + " doesn't exist."));

        ServiceTask serviceTask = serviceTaskRepository.findById(serviceTaskId)
                .orElseThrow(
                        () -> new RuntimeException("Service task with an id:  " + serviceTaskId + " doesn't exist."));

        ticket.getTasks().add(serviceTask);

        ticketRepository.save(ticket);
    }

    // Technician
    @Transactional
    public void removeServiceTaskFromTicket(Long ticketId, Long serviceTaskId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket with an id:  " + ticketId + " doesn't exist."));

        ServiceTask serviceTask = serviceTaskRepository.findById(serviceTaskId)
                .orElseThrow(
                        () -> new RuntimeException("Service task with an id:  " + serviceTaskId + " doesn't exist."));

        ticket.getTasks().remove(serviceTask);

        ticketRepository.save(ticket);
    }

    // Technician
    public BigDecimal calculateTotalCost(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket with an id:  " + ticketId + " doesn't exist."));

        BigDecimal totalCost = ticket.getTasks().stream()
                .map(ServiceTask::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return Optional.of(totalCost)
                .filter(cost -> cost.compareTo(new BigDecimal("500")) > 0) // Przy compare to jesli wynikiem jest -1 to
                                                                           // liczba jest mniejsza, jesli 0 to jest
                                                                           // rowna a jesli 1 to wieksza. Dlatego jest > 0
                                                                           // na koncu.
                .map(cost -> cost.multiply(new BigDecimal("0.90")))
                .orElse(totalCost);
    }

    public int calculateETA(Long technicianId) {
        List<Ticket> activeTickets = ticketRepository.findAllByTechnicianIdAndTicketStatus(technicianId,
                TicketStatus.UNDER_REPAIRS);

        int totalTime = activeTickets.stream()
                .flatMap(ticket -> ticket.getTasks().stream())
                .mapToInt(ServiceTask::getEstimatedTimeInMinutes)
                .sum();

        return totalTime;
    }
}
