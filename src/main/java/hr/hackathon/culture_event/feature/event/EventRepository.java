package hr.hackathon.culture_event.feature.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("SELECT e FROM Event e ORDER BY e.id DESC LIMIT 50")
    List<Event> findAllLimit50();

}
