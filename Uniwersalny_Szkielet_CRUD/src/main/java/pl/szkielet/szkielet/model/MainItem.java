package pl.szkielet.szkielet.model;
import java.math.BigDecimal;
import jakarta.persistence.*;
import lombok.*;
@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class MainItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private String name;
    private int capacity;
    private BigDecimal price;
    private boolean available;
}
