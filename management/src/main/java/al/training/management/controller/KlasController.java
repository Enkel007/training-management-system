package al.training.management.controller;

import al.training.management.dto.ClassDto;
import al.training.management.exception.AlreadyExistsException;
import al.training.management.exception.ResourceNotFoundException;
import al.training.management.model.Klas;
import al.training.management.request.CreateClassRequest;
import al.training.management.request.UpdateClassRequest;
import al.training.management.response.ApiResponse;
import al.training.management.service.klas.IKlasService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/classes")
public class KlasController {
    private final IKlasService klasService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts() {
        List<Klas> classes = klasService.getAllClasses();
        List<ClassDto> convertedClasses = klasService.getConvertedClasses(classes);
        return  ResponseEntity.ok(new ApiResponse("Success!", convertedClasses));
    }

    @GetMapping("/{classId}/class")
    public ResponseEntity<ApiResponse> getClassById(@PathVariable Long classId) {
        try {
            Klas klas = klasService.getClassById(classId);
            ClassDto classDto = klasService.convertClassToDto(klas);
            return ResponseEntity.ok(new ApiResponse("Success!", classDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createClass(@RequestBody CreateClassRequest request) {
        try {
            Klas klas = klasService.addClass(request);
            ClassDto classDto= klasService.convertClassToDto(klas);
            return ResponseEntity.ok(new ApiResponse("Class created successfully!", classDto));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/{classId}/update")
    public ResponseEntity<ApiResponse> updateClass(@RequestBody UpdateClassRequest request, @PathVariable Long classId) {
        try {
            Klas klas = klasService.updateClass(request, classId);
            ClassDto classDto = klasService.convertClassToDto(klas);
            return ResponseEntity.ok(new ApiResponse("Class updated successfully!", classDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{classId}/delete")
    public ResponseEntity<ApiResponse> deleteClass(@PathVariable Long classId) {
        try {
            klasService.deleteClass(classId);
            return ResponseEntity.ok(new ApiResponse("Class deleted successfully!", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/classes/{subject}/classes")
    public ResponseEntity<ApiResponse> getProductByName(@PathVariable String subject){
        try {
            List<Klas> classes = klasService.getClassBySubject(subject);
            if (classes.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No classes found ", null));
            }
            List<ClassDto> convertedClasses = klasService.getConvertedClasses(classes);
            return  ResponseEntity.ok(new ApiResponse("Successful!", convertedClasses));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error!", e.getMessage()));
        }
    }

    @GetMapping("/classes/by/block-and-subject")
    public ResponseEntity<ApiResponse> getClassesByBlockAndSubject(@RequestParam String blockName, @RequestParam String subject) {
        try {
            List<Klas> classes = klasService.getClassByBlockAndSubject(blockName, subject);
            if (classes.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No classes found ", null));
            }
            List<ClassDto> convertedClasses = klasService.getConvertedClasses(classes);
            return  ResponseEntity.ok(new ApiResponse("Successful!", convertedClasses));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/classes/{subject}/classes")
    public ResponseEntity<ApiResponse> getClassBySubject(@PathVariable String subject){
        try {
            List<Klas> classes = klasService.getClassBySubject(subject);
            if (classes.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found ", null));
            }
            List<ClassDto> convertedClasses = klasService.getConvertedClasses(classes);
            return  ResponseEntity.ok(new ApiResponse("success", convertedClasses));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("error", e.getMessage()));
        }
    }

    @GetMapping("/class/{block}/all/classes")
    public ResponseEntity<ApiResponse> findClassesByBlock(@PathVariable String block) {
        try {
            List<Klas> classes = klasService.getClassesByBlock(block);
            if (classes.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No classes found ", null));
            }
            List<ClassDto> convertedClasses = klasService.getConvertedClasses(classes);
            return  ResponseEntity.ok(new ApiResponse("success", convertedClasses));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
        }
    }
}
