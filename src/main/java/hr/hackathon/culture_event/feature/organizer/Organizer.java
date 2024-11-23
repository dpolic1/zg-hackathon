package hr.hackathon.culture_event.feature.organizer;

import hr.hackathon.culture_event.feature.event.Event;
import hr.hackathon.culture_event.feature.organizer_type.OrganizerType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table
public class Organizer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "organizers_types",
      joinColumns = @JoinColumn(name = "organizer_id"),
      inverseJoinColumns = @JoinColumn(name = "organizer_type_id"))
  private List<OrganizerType> types;

  @OneToMany private List<Event> events;

  private String location;

  private String contactEmail;

  private Boolean parkingAvailableFlag;

  private Boolean animalsAllowedFlag;

  private Boolean disabilityEntranceFlag;

  private Boolean disabilityToiletFlag;
}
