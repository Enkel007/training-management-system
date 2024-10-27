package al.training.management.service.course;

import al.training.management.dto.CourseDto;
import al.training.management.exception.AlreadyExistsException;
import al.training.management.exception.ResourceNotFoundException;
import al.training.management.model.Course;
import al.training.management.repository.CourseRepository;
import al.training.management.request.CreateCourseRequest;
import al.training.management.request.UpdateCourseRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CourseService implements ICourseService {
    private final CourseRepository courseRepository;

    private final ModelMapper modelMapper;

    @Override
    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
    }

    @Override
    public Course addCourse(CreateCourseRequest request) { //pyt profesorin
        if (courseExists(request.getName())){
            throw new AlreadyExistsException("Course with name" + request.getName()
                    + " already exists, you may update this course instead!");
        }
        return courseRepository.save(createCourse(request));
    }

    private Course createCourse(CreateCourseRequest request) {
        return new Course(
                request.getName()
        );
    }

    private boolean courseExists(String name) {
        return courseRepository.existsByName(name);
    }

    @Override
    public Course updateCourse(UpdateCourseRequest request, Long id) {
        return courseRepository.findById(id)
                .map(existingCourse -> updateExistingCourse(existingCourse,request))
                .map(courseRepository :: save)
                .orElseThrow(()-> new ResourceNotFoundException("Course not found!"));
    }

    private Course updateExistingCourse(Course existingCourse, UpdateCourseRequest request) {
        existingCourse.setName(request.getName());
        return existingCourse;
    }

    @Override
    public void deleteCourse(Long id) {
        courseRepository.findById(id)
                .ifPresentOrElse(courseRepository::delete,
                        () -> {throw new ResourceNotFoundException("Course not found!");});
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public List<Course> getCourseByName(String name) {
        return courseRepository.findByName(name);
    }

    @Override
    public CourseDto convertToDto(Course course) {
        return modelMapper.map(course, CourseDto.class);
    }

    @Override
    public List<CourseDto> getConvertedCourses(List<Course> courses) {
        return courses.stream().map(this::convertToDto).toList();
    }
}
