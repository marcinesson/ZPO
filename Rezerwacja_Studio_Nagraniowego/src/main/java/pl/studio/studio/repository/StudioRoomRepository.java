package pl.studio.studio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.studio.studio.model.StudioRoom;

public interface StudioRoomRepository extends JpaRepository<StudioRoom, Long> {
}
