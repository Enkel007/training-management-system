package al.training.management.repository;


import al.training.management.model.Klas;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Date;
import java.util.List;

public interface KlasRepository extends JpaRepository<Klas, Long> {
    boolean existsBySubjectAndDate(String subject, Date date);
    
    List<Klas> findByBlockOfClasses_Name(String blockName);
    
    List<Klas> findBySubject(String subject);
    
    List<Klas> findByBlockOfClasses_NameAndSubject(String blockName, String subject);
}
