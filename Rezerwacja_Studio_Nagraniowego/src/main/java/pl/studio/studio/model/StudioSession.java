package pl.studio.studio.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "studio_session")
public class StudioSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "resource_id")
    private StudioRoom resource;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    private int requiredCapacity;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private BigDecimal totalPrice;
}
