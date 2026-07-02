package pl.biblioteka.biblioteka.service;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import pl.biblioteka.biblioteka.model.*;
import pl.biblioteka.biblioteka.repository.*;

@Service @RequiredArgsConstructor @Transactional
public class LoanService {
    private final BookRepository itemRepository;
    private final LoanRepository recordRepository;
    private final AppUserService appUserService;

    public Book findBestAvailableItem(int requiredCapacity, LocalDateTime startTime, LocalDateTime endTime) {
        return itemRepository.findAll().stream()
            .filter(b -> b.isAvailable())
            .filter(b -> b.getCapacity() >= requiredCapacity)
            .filter(b -> recordRepository.findAll().stream()
                .noneMatch(record -> record.getItem().getId().equals(b.getId())
                    && record.getStartTime() != null
                    && record.getEndTime() != null
                    && record.getStartTime().isBefore(endTime)
                    && record.getEndTime().isAfter(startTime)))
            .sorted(Comparator.comparing(Book::getCapacity))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("No available item found."));
    }

    public BigDecimal calculatePrice(Book item, LocalDateTime startTime, LocalDateTime endTime) {
        long hours = Math.max(1, Duration.between(startTime, endTime).toHours());
        return item.getPrice().multiply(BigDecimal.valueOf(hours));
    }

    public Loan createRecord(Long itemId, LocalDateTime startTime, LocalDateTime endTime) {
        validateDates(startTime, endTime);
        Book item = itemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("Item not found."));
        Optional.of(item)
            .filter(b -> b.isAvailable())
            .orElseThrow(() -> new IllegalStateException("Item is not available."));
        boolean conflict = recordRepository.findAll().stream()
            .anyMatch(record -> record.getItem().getId().equals(itemId)
                && record.getStartTime() != null
                && record.getEndTime() != null
                && record.getStartTime().isBefore(endTime)
                && record.getEndTime().isAfter(startTime));
        Optional.of(conflict).filter(value -> !value).orElseThrow(() -> new IllegalStateException("Time conflict."));
        Loan record = new Loan();
        record.setItem(item);
        record.setUser(appUserService.getLoggedInUser());
        record.setStartTime(startTime);
        record.setEndTime(endTime);
        record.setStatus("ACTIVE");
        record.setTotalPrice(calculatePrice(item, startTime, endTime));
        return recordRepository.save(record);
    }

    public List<Book> getAllItems() {
        return itemRepository.findAll();
    }

    public List<Loan> getMyRecords() {
        AppUser user = appUserService.getLoggedInUser();
        return recordRepository.findAll().stream()
            .filter(record -> record.getUser().getId().equals(user.getId()))
            .toList();
    }

    public List<Loan> getAllRecords() {
        return recordRepository.findAll();
    }

    private void validateDates(LocalDateTime startTime, LocalDateTime endTime) {
        Optional.ofNullable(startTime)
            .filter(start -> endTime != null)
            .filter(start -> !start.isBefore(LocalDateTime.now()))
            .filter(start -> start.isBefore(endTime))
            .orElseThrow(() -> new IllegalArgumentException("Wrong dates."));
    }
}
