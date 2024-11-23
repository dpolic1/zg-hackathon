package hr.hackathon.culture_event.feature.event_category;

import hr.hackathon.culture_event.feature.event_type.EventType;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventTypeMapper {

    EventTypeResponse toResponse(EventType eventType);

    List<EventTypeResponse> toResponseList(List<EventType> eventTypes);

}
