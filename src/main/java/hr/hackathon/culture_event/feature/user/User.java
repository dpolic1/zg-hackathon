package hr.hackathon.culture_event.feature.user;

import hr.hackathon.culture_event.feature.creation_audit.CreationAudit;
import hr.hackathon.culture_event.feature.user.role.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends CreationAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String firstName;

  private String lastName;

  private String username;

  private String email;

  private String password;

  @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
  @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
  @Column(name = "role", nullable = false)
  @Enumerated(EnumType.STRING)
  private Set<Role> roles = new HashSet<>();
}
