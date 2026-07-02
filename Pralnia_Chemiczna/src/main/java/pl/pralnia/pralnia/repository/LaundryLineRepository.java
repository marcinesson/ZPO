package pl.pralnia.pralnia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pralnia.pralnia.model.LaundryLine;

public interface LaundryLineRepository extends JpaRepository<LaundryLine, Long> {
}
