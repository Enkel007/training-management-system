package al.training.management.repository;

import al.training.management.model.BlockOfClasses;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockOfClassesRepository extends JpaRepository<BlockOfClasses, Long> {
    BlockOfClasses findByName(String name);

    boolean existsByName(String name);
}
