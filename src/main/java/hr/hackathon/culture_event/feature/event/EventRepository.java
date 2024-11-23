package hr.hackathon.culture_event.feature.event;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

  @Query("SELECT e FROM Event e WHERE e.eventImageUrl IS NOT NULL ORDER BY e.id ASC LIMIT 50")
  List<Event> findAllLimit50();

  @Query("SELECT e FROM Event e")
  @EntityGraph(attributePaths = {"eventCategories"})
  List<Event> findAllWithCategories();

  @Query(
      """
              SELECT e FROM Event e
              WHERE
                    LOWER(e.shortName) LIKE LOWER(CONCAT('%', ?1, '%')) OR
                    LOWER(e.longName) LIKE LOWER(CONCAT('%', ?1, '%')) OR
                    LOWER(e.englishName) LIKE LOWER(CONCAT('%', ?1, '%')) OR
                    LOWER(e.location) LIKE LOWER(CONCAT('%', ?1, '%')) OR
                    e.description LIKE LOWER(CONCAT('%', ?1, '%')) OR
                    e.descriptionEnglish LIKE LOWER(CONCAT('%', ?1, '%')) OR
                    LOWER(e.organizer.name) LIKE LOWER(CONCAT('%', ?1, '%')) OR
                    LOWER(e.neighbourhood.name) LIKE LOWER(CONCAT('%', ?1, '%'))
              ORDER BY e.id ASC LIMIT 50
              """)
  List<Event> searchEvents(@Param("searchTerm") String searchTerm);

  @Query(
      """
        SELECT e FROM Event e
        WHERE
            (:searchTerm IS NULL OR
             LOWER(e.shortName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR
             LOWER(e.longName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR
             LOWER(e.englishName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR
             LOWER(e.location) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR
             LOWER(e.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR
             LOWER(e.descriptionEnglish) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR
             LOWER(e.organizer.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR
             LOWER(e.neighbourhood.name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))
            ) AND
            (:fromDate IS NULL OR e.startDate >= :fromDate) AND
            (:toDate IS NULL OR e.startDate <= :toDate) AND
            (:maxPrice IS NULL OR e.price <= :maxPrice) AND
            (:categoryId IS NULL OR EXISTS (
                SELECT 1 FROM e.eventCategories ec WHERE ec.id = :categoryId
            ))
        """)
  List<Event> searchEventsWithFilters(
      @Param("searchTerm") String searchTerm,
      @Param("fromDate") LocalDateTime fromDate,
      @Param("toDate") LocalDateTime toDate,
      @Param("maxPrice") Double maxPrice,
      @Param("categoryId") Long categoryId);
}
