package pl.stroje.stroje.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "costume_rental")
public class CostumeRental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "resource_id")
    private Costume resource;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    private int requiredCapacity;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private BigDecimal totalPrice;
}
