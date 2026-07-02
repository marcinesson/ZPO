package pl.instrumenty.instrumenty.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "instrument_rental")
public class InstrumentRental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "resource_id")
    private Instrument resource;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    private int requiredCapacity;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private BigDecimal totalPrice;
}
