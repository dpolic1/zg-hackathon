package hr.hackathon.culture_event.feature.event;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EventResourceService {

  private final EventRepository eventRepository;
  private final EventMapper eventMapper;

  public List<EventResponse> findAll() {
    List<Event> events = eventRepository.findAllLimit50();
    return events.stream().map(eventMapper::toResponse).toList();
  }

  public EventResponse findById(Long id) {
    return eventMapper.toResponse(
        eventRepository
            .findById(id)
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found")));
  }

  public List<EventResponse> searchEventsWithFilters(
      String searchTerm,
      LocalDateTime fromDate,
      LocalDateTime toDate,
      Double maxPrice,
      Long categoryId) {

    List<Event> searchedEvents =
        eventRepository.searchEventsWithFilters(searchTerm, maxPrice, categoryId);

    return searchedEvents.stream()
        .filter(
            event -> {
              if (fromDate != null && event.getStartDate().isBefore(fromDate)) {
                return false;
              }
              if (toDate != null && event.getStartDate().isAfter(toDate)) {
                return false;
              }
              return true;
            })
        .map(eventMapper::toResponse)
        .toList();
  }
}
