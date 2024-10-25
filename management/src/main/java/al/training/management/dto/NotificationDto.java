package al.training.management.dto;

import lombok.Data;

@Data
public class NotificationDto {
    private Long id;
    private String subject;
    private String content;
    private boolean read;
    private String timeStamp;
    private UserDto user;
    private ClassDto classes;
}
