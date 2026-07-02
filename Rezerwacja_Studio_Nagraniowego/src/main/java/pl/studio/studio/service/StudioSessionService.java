package pl.studio.studio.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import pl.studio.studio.model.*;
import pl.studio.studio.repository.StudioSessionRepository;
import pl.studio.studio.repository.StudioRoomRepository;

@Service
@RequiredArgsConstructor
public class StudioSessionService {
    private final StudioRoomRepository resourceRepository;
    private final StudioSessionRepository recordRepository;
    private final AppUserService appUserService;

    public List<StudioRoom> getAllResources() {
        return resourceRepository.findAll();
    }

    public List<StudioSession> getAllRecords() {
        return recordRepository.findAll();
    }

    public List<StudioSession> getMine() {
        return recordRepository.findByUserId(appUserService.getLoggedInUser().getId());
    }

    public StudioRoom findBestResource(StudioType category, int requiredCapacity,
            LocalDateTime startTime, LocalDateTime endTime) {
        validateSearch(category, requiredCapacity, startTime, endTime);

        return resourceRepository.findAll().stream()
                .filter(StudioRoom::isAvailable)
                .filter(resource -> resource.getCategory() == category)
                .filter(resource -> resource.getCapacity() >= requiredCapacity)
                .filter(resource -> !recordRepository.existsOverlapping(resource.getId(), startTime, endTime))
                .sorted(Comparator.comparing(StudioRoom::getCapacity)
                        .thenComparing(StudioRoom::getPrice))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No matching resource available."));
    }

    @Transactional
    public StudioSession createRecord(StudioType category, int requiredCapacity,
            LocalDateTime startTime, LocalDateTime endTime) {
        StudioRoom resource = findBestResource(category, requiredCapacity, startTime, endTime);
        StudioSession record = new StudioSession();
        record.setResource(resource);
        record.setUser(appUserService.getLoggedInUser());
        record.setRequiredCapacity(requiredCapacity);
        record.setStartTime(startTime);
        record.setEndTime(endTime);
        record.setTotalPrice(calculatePrice(resource, requiredCapacity, startTime, endTime));
        return recordRepository.save(record);
    }

    public BigDecimal calculatePrice(StudioRoom resource, int requiredCapacity,
            LocalDateTime startTime, LocalDateTime endTime) {
        validateSearch(resource.getCategory(), requiredCapacity, startTime, endTime);
        long hours = Math.max(1, ChronoUnit.HOURS.between(startTime, endTime));
        BigDecimal capacityMultiplier = BigDecimal.ONE
                .add(BigDecimal.valueOf(Math.max(0, requiredCapacity - 1)).multiply(new BigDecimal("0.10")));
        return resource.getPrice()
                .multiply(BigDecimal.valueOf(hours))
                .multiply(capacityMultiplier)
                .setScale(2, RoundingMode.HALF_UP);
    }

    @Transactional
    public void cancelRecord(Long id) {
        recordRepository.deleteById(id);
    }

    private void validateSearch(StudioType category, int requiredCapacity,
            LocalDateTime startTime, LocalDateTime endTime) {
        Optional.ofNullable(category)
                .orElseThrow(() -> new RuntimeException("Category is required."));
        Optional.of(requiredCapacity)
                .filter(value -> value > 0)
                .orElseThrow(() -> new RuntimeException("Required capacity must be positive."));
        Optional.ofNullable(startTime)
                .filter(start -> endTime != null)
                .filter(start -> !start.isAfter(endTime))
                .filter(start -> !start.isBefore(LocalDateTime.now().minusMinutes(1)))
                .orElseThrow(() -> new RuntimeException("Invalid time range."));
    }
}
