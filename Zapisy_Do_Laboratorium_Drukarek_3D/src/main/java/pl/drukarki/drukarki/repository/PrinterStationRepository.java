package pl.drukarki.drukarki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.drukarki.drukarki.model.PrinterStation;

public interface PrinterStationRepository extends JpaRepository<PrinterStation, Long> {
}
