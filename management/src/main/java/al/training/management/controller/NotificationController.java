package al.training.management.controller;

import al.training.management.dto.NotificationDto;
import al.training.management.exception.AlreadyExistsException;
import al.training.management.model.Notification;
import al.training.management.request.CreateNotificationRequest;
import al.training.management.response.ApiResponse;
import al.training.management.service.notification.INotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CONFLICT;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/notifications")
public class NotificationController {
    private final INotificationService notificationService;

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @PostMapping("/send")
    public ResponseEntity<ApiResponse> sendNotification(@RequestBody CreateNotificationRequest notification) {
        try {
            Notification theNotification = notificationService
                    .createNotification(notification.getClassId(), notification.getSubject(), notification.getContent());
            NotificationDto notificationDto = notificationService.convertNotificationToDto(theNotification);
            return ResponseEntity.ok(new ApiResponse("Notification sent successfully!", notificationDto));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/unread")
    public ResponseEntity<ApiResponse> getUnreadNotifications() {
        List<Notification> notifications = notificationService.getUnreadNotifications();
        List<NotificationDto> convertedNotifications = notificationService.getConvertedNotifications(notifications);
        return ResponseEntity.ok(new ApiResponse("Success!", convertedNotifications));
    }

    @PostMapping("/read")
    public ResponseEntity<Void> markAsRead(@RequestParam Long classId) {
        notificationService.markAsRead(classId);
        return ResponseEntity.noContent().build();
    }
}