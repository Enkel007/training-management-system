package al.training.management.request;

import lombok.Data;

@Data
public class UpdateCourseRequest {
    private Long id;
    private String name;
}
