package al.training.management.service.course;

import al.training.management.dto.CourseDto;
import al.training.management.model.Course;
import al.training.management.request.CreateCourseRequest;
import al.training.management.request.UpdateCourseRequest;

import java.util.List;

public interface ICourseService {
    Course getCourseById(Long id);

    Course addCourse(CreateCourseRequest request);

    Course updateCourse(UpdateCourseRequest request, Long id);

    void deleteCourse(Long id);

    List<Course> getAllCourses();

    List<Course> getCourseByName(String name);

    CourseDto convertToDto(Course course);

    List<CourseDto> getConvertedCourses(List<Course> courses);
}
