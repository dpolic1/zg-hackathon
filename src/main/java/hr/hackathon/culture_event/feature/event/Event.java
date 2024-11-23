package hr.hackathon.culture_event.feature.event;

import hr.hackathon.culture_event.feature.creation_audit.CreationAudit;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "events")
public class Event extends CreationAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String shortName;

  private String longName;

  private String englishName;

//  @ManyToOne(fetch = FetchType.LAZY)
//  private Organizer organizer;
//
//  private String location;
//
//  private Neighbourhood neighbourhood;
//
//  private LocalDateTime startDate;
//
//  private LocalDateTime endDate;
//
//  private Boolean childFriendlyFlag;
//
//  private List<AgeGroup> ageGroups;
}
