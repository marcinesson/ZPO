package pl.foto.foto.service;

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
import pl.foto.foto.model.*;
import pl.foto.foto.repository.PhotoSessionRepository;
import pl.foto.foto.repository.PhotoStudioSetRepository;

@Service
@RequiredArgsConstructor
public class PhotoSessionService {
    private final PhotoStudioSetRepository resourceRepository;
    private final PhotoSessionRepository recordRepository;
    private final AppUserService appUserService;

    public List<PhotoStudioSet> getAllResources() {
        return resourceRepository.findAll();
    }

    public List<PhotoSession> getAllRecords() {
        return recordRepository.findAll();
    }

    public List<PhotoSession> getMine() {
        return recordRepository.findByUserId(appUserService.getLoggedInUser().getId());
    }

    public PhotoStudioSet findBestResource(SessionType category, int requiredCapacity,
            int minQuality, int taskUnits, int priority, LocalDateTime startTime, LocalDateTime endTime) {
        validateSearch(category, requiredCapacity, minQuality, taskUnits, priority, startTime, endTime);
        int availableHours = calculateStartedHours(startTime, endTime);

        return resourceRepository.findAll().stream()
                .filter(PhotoStudioSet::isAvailable)
                .filter(resource -> resource.getCategory() == category)
                .filter(resource -> resource.getCapacity() >= requiredCapacity)
                .filter(resource -> resource.getQuality() >= minQuality)
                .filter(resource -> resource.getUnitsPerHour() > 0)
                .filter(resource -> !recordRepository.existsOverlapping(resource.getId(), startTime, endTime))
                .filter(resource -> calculateRequiredHours(resource, taskUnits) <= availableHours)
                .min(Comparator.comparing(resource -> calculateScore(resource, requiredCapacity,
                        minQuality, taskUnits, priority)))
                .orElseThrow(() -> new RuntimeException("No matching resource available."));
    }

    @Transactional
    public PhotoSession createRecord(SessionType category, int requiredCapacity,
            int minQuality, int taskUnits, int priority, LocalDateTime startTime, LocalDateTime endTime) {
        PhotoStudioSet resource = findBestResource(category, requiredCapacity, minQuality,
                taskUnits, priority, startTime, endTime);
        PhotoSession record = new PhotoSession();
        record.setResource(resource);
        record.setUser(appUserService.getLoggedInUser());
        record.setCategory(category);
        record.setRequiredCapacity(requiredCapacity);
        record.setMinQuality(minQuality);
        record.setTaskUnits(taskUnits);
        record.setPriority(priority);
        record.setCalculatedHours(calculateRequiredHours(resource, taskUnits));
        record.setStartTime(startTime);
        record.setEndTime(endTime);
        record.setScore(calculateScore(resource, requiredCapacity, minQuality, taskUnits, priority));
        record.setTotalPrice(calculatePrice(resource, taskUnits, priority));
        return recordRepository.save(record);
    }

    public BigDecimal calculateScore(PhotoStudioSet resource, int requiredCapacity,
            int minQuality, int taskUnits, int priority) {
        BigDecimal price = calculatePrice(resource, taskUnits, priority);
        BigDecimal capacityPenalty = BigDecimal.valueOf(resource.getCapacity() - requiredCapacity)
                .multiply(new BigDecimal("3.00"));
        BigDecimal qualityPenalty = BigDecimal.valueOf(resource.getQuality() - minQuality)
                .multiply(new BigDecimal("8.00"));
        BigDecimal priorityPenalty = BigDecimal.valueOf(6L - priority)
                .multiply(new BigDecimal("4.00"));

        return price.add(capacityPenalty)
                .add(qualityPenalty)
                .add(priorityPenalty)
                .setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculatePrice(PhotoStudioSet resource, int taskUnits, int priority) {
        int hours = calculateRequiredHours(resource, taskUnits);
        BigDecimal priorityMultiplier = BigDecimal.ONE
                .add(BigDecimal.valueOf(Math.max(0, priority - 1)).multiply(new BigDecimal("0.05")));
        return resource.getPricePerHour()
                .multiply(BigDecimal.valueOf(hours))
                .multiply(priorityMultiplier)
                .setScale(2, RoundingMode.HALF_UP);
    }

    public int calculateRequiredHours(PhotoStudioSet resource, int taskUnits) {
        return Math.max(1, (taskUnits + resource.getUnitsPerHour() - 1) / resource.getUnitsPerHour());
    }

    @Transactional
    public void cancelRecord(Long id) {
        recordRepository.deleteById(id);
    }

    private int calculateStartedHours(LocalDateTime startTime, LocalDateTime endTime) {
        long minutes = ChronoUnit.MINUTES.between(startTime, endTime);
        return Math.max(1, (int) ((minutes + 59) / 60));
    }

    private void validateSearch(SessionType category, int requiredCapacity,
            int minQuality, int taskUnits, int priority, LocalDateTime startTime, LocalDateTime endTime) {
        Optional.ofNullable(category)
                .orElseThrow(() -> new RuntimeException("Category is required."));
        Optional.of(requiredCapacity)
                .filter(value -> value > 0)
                .orElseThrow(() -> new RuntimeException("Required capacity must be positive."));
        Optional.of(minQuality)
                .filter(value -> value > 0)
                .orElseThrow(() -> new RuntimeException("Minimum quality must be positive."));
        Optional.of(taskUnits)
                .filter(value -> value > 0)
                .orElseThrow(() -> new RuntimeException("Task units must be positive."));
        Optional.of(priority)
                .filter(value -> value >= 1 && value <= 5)
                .orElseThrow(() -> new RuntimeException("Priority must be between 1 and 5."));
        Optional.ofNullable(startTime)
                .filter(start -> endTime != null)
                .filter(start -> start.isBefore(endTime))
                .filter(start -> !start.isBefore(LocalDateTime.now().minusMinutes(1)))
                .orElseThrow(() -> new RuntimeException("Invalid time range."));
    }
}
