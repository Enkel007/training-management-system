package al.training.management.repository;

import al.training.management.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    boolean existsByName(String name);

    List<Course> findByName(String name);
}
