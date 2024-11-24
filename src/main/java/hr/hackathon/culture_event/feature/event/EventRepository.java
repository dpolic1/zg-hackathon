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
      value =
          """
        SELECT e.* FROM events e
                LEFT JOIN organizers o ON e.organizer_id = o.id
                LEFT JOIN neighbourhoods n ON e.neighbourhood_id = n.id
                LEFT JOIN events_categories ec ON ec.event_id = e.id
                LEFT JOIN event_categories c ON ec.category_id = c.id
        WHERE
            (:searchTerm IS NULL OR
             LOWER(e.short_name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR
             LOWER(e.long_name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR
             LOWER(e.english_name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR
             LOWER(e.location) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR
             LOWER(e.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR
             LOWER(e.description_english) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR
             LOWER(o.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR
             LOWER(n.name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))
            ) AND
/*            (:fromDate IS NULL OR e.start_date >= :fromDate) AND
            (:toDate IS NULL OR e.start_date <= :toDate) AND*/
            (:maxPrice IS NULL OR e.price <= :maxPrice) AND
            (:categoryIds IS NULL OR ec.category_id IN :categoryIds)
    """,
      nativeQuery = true)
  List<Event> searchEventsWithFilters(
      @Param("searchTerm") String searchTerm,
      @Param("maxPrice") Double maxPrice,
      @Param("categoryIds") List<Long> categoryIds);
}
