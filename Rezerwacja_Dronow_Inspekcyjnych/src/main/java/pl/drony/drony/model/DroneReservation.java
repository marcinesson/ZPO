package pl.drony.drony.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "drone_reservation")
public class DroneReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "resource_id")
    private InspectionDrone resource;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    private int requiredCapacity;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private BigDecimal totalPrice;
}
