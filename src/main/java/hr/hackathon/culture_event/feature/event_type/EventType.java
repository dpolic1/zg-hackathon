//package hr.hackathon.culture_event.feature.event_type;
//
//import hr.hackathon.culture_event.feature.event.Event;
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.util.List;
//
//@Getter
//@Setter
//@Entity
//@Table(name = "event_types")
//public class EventType {
//
//  @Id
//  @GeneratedValue(strategy = GenerationType.IDENTITY)
//  private Long id;
//
//  private String name;
//
//  @OneToMany
//  private List<Event> events;
//}
