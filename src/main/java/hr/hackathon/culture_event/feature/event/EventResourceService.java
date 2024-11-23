package hr.hackathon.culture_event.feature.event;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EventResourceService {

  private final EventRepository eventRepository;
  private final EventMapper eventMapper;

  public List<EventResponse> findAll(){
    List<Event> events = eventRepository.findAll();
    return events.stream().map(eventMapper::toResponse).toList();
  }

  public EventResponse findById(Long id) {
    return eventMapper.toResponse(
        eventRepository
            .findById(id)
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found")));
  }
}
