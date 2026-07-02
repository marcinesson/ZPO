package pl.bowling.bowling.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "lane_booking")
public class LaneBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "resource_id")
    private BowlingLane resource;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    private int requiredCapacity;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private BigDecimal totalPrice;
}
