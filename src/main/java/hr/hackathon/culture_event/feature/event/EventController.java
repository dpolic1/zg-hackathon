package hr.hackathon.culture_event.feature.event;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

  private final EventResourceService eventResourceService;

  @GetMapping()
  public List<EventResponse> findAll() {
    return eventResourceService.findAll();
  }

  @GetMapping("/{id}")
  public EventResponse findById(@PathVariable Long id) {
    return eventResourceService.findById(id);
  }
}
