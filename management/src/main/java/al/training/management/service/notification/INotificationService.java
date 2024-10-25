package al.training.management.service.notification;

import al.training.management.dto.ClassDto;
import al.training.management.dto.NotificationDto;
import al.training.management.model.Klas;
import al.training.management.model.Notification;
import al.training.management.repository.NotificationRepository;

import java.util.List;

public interface INotificationService {
    Notification createNotification(Long classId, String subject, String content);

    List<Notification> getUnreadNotifications();

    void markAsRead(Long id);

    NotificationDto convertNotificationToDto(Notification notification);

    List<NotificationDto> getConvertedNotifications(List<Notification> notifications);
}
