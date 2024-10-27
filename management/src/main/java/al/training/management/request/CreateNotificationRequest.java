package al.training.management.request;

import lombok.Data;

@Data
public class CreateNotificationRequest {
    private Long classId;
    private String subject;
    private String content;
}
