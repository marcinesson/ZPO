package pl.instrumenty.instrumenty.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.instrumenty.instrumenty.model.Instrument;

public interface InstrumentRepository extends JpaRepository<Instrument, Long> {
}
