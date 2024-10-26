package al.training.management.controller;

import al.training.management.dto.CourseDto;
import al.training.management.exception.AlreadyExistsException;
import al.training.management.exception.ResourceNotFoundException;
import al.training.management.model.Course;
import al.training.management.request.CreateCourseRequest;
import al.training.management.request.UpdateCourseRequest;
import al.training.management.response.ApiResponse;
import al.training.management.service.course.ICourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/courses")
public class CourseController {
    private final ICourseService courseService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        List<CourseDto> convertedCourses = courseService.getConvertedCourses(courses);
        return  ResponseEntity.ok(new ApiResponse("success", convertedCourses));
    }

    @GetMapping("course/{courseId}/course")
    public ResponseEntity<ApiResponse> getCourseById(@PathVariable Long courseId) {
        try {
            Course course = courseService.getCourseById(courseId);
            CourseDto courseDto = courseService.convertToDto(course);
            return  ResponseEntity.ok(new ApiResponse("success", courseDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCourse(@RequestBody CreateCourseRequest course) {
        try {
            Course theCourse = courseService.addCourse(course);
            CourseDto courseDto = courseService.convertToDto(theCourse);
            return ResponseEntity.ok(new ApiResponse("Course added successfully!", courseDto));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/course/{courseId}/update")
    public  ResponseEntity<ApiResponse> updateCourse(@RequestBody UpdateCourseRequest request, @PathVariable Long courseId) {
        try {
            Course theCourse = courseService.updateCourse(request, courseId);
            CourseDto courseDto = courseService.convertToDto(theCourse);
            return ResponseEntity.ok(new ApiResponse("Course updated successfully!", courseDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/course/{courseId}/delete")
    public ResponseEntity<ApiResponse> deleteCourse(@PathVariable Long courseId) {
        try {
            courseService.deleteCourse(courseId);
            return ResponseEntity.ok(new ApiResponse("Course deleted successfully!", courseId));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/courses/{name}/courses")
    public ResponseEntity<ApiResponse> getCourseByName(@PathVariable String name){
        try {
            List<Course> courses = courseService.getCourseByName(name);
            if (courses.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No courses found!", null));
            }
            List<CourseDto> convertedCourses = courseService.getConvertedCourses(courses);
            return  ResponseEntity.ok(new ApiResponse("success", convertedCourses));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("error", e.getMessage()));
        }
    }
}
