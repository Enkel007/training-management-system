package al.training.management.repository;

import al.training.management.model.Klas;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface KlasRepository extends JpaRepository<Klas,Long> {
    boolean existsBySubjectandDate(String subject, Date date);

    List<Klas> findByBlock(String block);

    List<Klas> findBySubject(String subject);

    List<Klas> findByBlockAndSubject(String block, String subject);
}
