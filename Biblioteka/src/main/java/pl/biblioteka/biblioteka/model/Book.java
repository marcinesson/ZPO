package pl.biblioteka.biblioteka.model;
import java.math.BigDecimal;
import jakarta.persistence.*;
import lombok.*;
@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class Book {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private String name;
    private int capacity;
    private BigDecimal price;
    private boolean available;
}
