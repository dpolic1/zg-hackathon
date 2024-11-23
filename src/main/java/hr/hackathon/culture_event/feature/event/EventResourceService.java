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
    List<Event> events = eventRepository.findAllWithCategories();

    String searchTermLower =
        (searchTerm != null && !searchTerm.isEmpty()) ? searchTerm.toLowerCase() : null;

    List<Event> searchedEvents =
        events.parallelStream()
            .filter(
                event -> {
                  // Only perform search filtering if searchTermLower is provided
                  if (searchTermLower != null) {
                    String searchTermLowerCopy =
                        searchTermLower
                            .toLowerCase(); // Use the precomputed search term for comparison

                    return (event.getShortName() != null
                            && event.getShortName().toLowerCase().contains(searchTermLowerCopy))
                        || (event.getLongName() != null
                            && event.getLongName().toLowerCase().contains(searchTermLowerCopy))
                        || (event.getEnglishName() != null
                            && event.getEnglishName().toLowerCase().contains(searchTermLowerCopy))
                        || (event.getLocation() != null
                            && event.getLocation().toLowerCase().contains(searchTermLowerCopy))
                        || (event.getDescription() != null
                            && event.getDescription().toLowerCase().contains(searchTermLowerCopy))
                        || (event.getDescriptionEnglish() != null
                            && event
                                .getDescriptionEnglish()
                                .toLowerCase()
                                .contains(searchTermLowerCopy))
                        || (event.getOrganizer() != null
                            && event.getOrganizer().getName() != null
                            && event
                                .getOrganizer()
                                .getName()
                                .toLowerCase()
                                .contains(searchTermLowerCopy))
                        || (event.getNeighbourhood() != null
                            && event.getNeighbourhood().getName() != null
                            && event
                                .getNeighbourhood()
                                .getName()
                                .toLowerCase()
                                .contains(searchTermLowerCopy));
                  }
                  return true; // If searchTerm is null or empty, return all events
                })
            .filter(
                event -> {
                  // Apply additional filters (fromDate, toDate, maxPrice, categoryId)
                  if (fromDate != null && event.getStartDate().isBefore(fromDate)) {
                    return false;
                  }
                  if (toDate != null && event.getStartDate().isAfter(toDate)) {
                    return false;
                  }
                  if(event.getPrice() == null){
                    return true;
                  }
                  if (maxPrice != null && event.getPrice() > maxPrice) {
                    return false;
                  }
                  if (categoryId != null) {
                    return event.getEventCategories().stream()
                        .anyMatch(category -> category.getId().equals(categoryId));
                  }
                  return true; // If no additional filters, return true
                })
            .toList();

    return searchedEvents.stream().map(eventMapper::toResponse).toList();
    /*return eventRepository.searchEventsWithFilters(
    searchTerm, fromDate, toDate, maxPrice, categoryId);*/
  }
}
