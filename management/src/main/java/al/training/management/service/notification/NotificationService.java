package al.training.management.service.notification;

import al.training.management.dto.NotificationDto;
import al.training.management.exception.ResourceNotFoundException;
import al.training.management.model.Klas;
import al.training.management.model.Notification;
import al.training.management.repository.KlasRepository;
import al.training.management.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class NotificationService implements INotificationService{
    private final NotificationRepository notificationRepository;
    private final KlasRepository klasRepository;

    private final ModelMapper modelMapper;

    @Override
    public Notification createNotification(Long classId, String subject, String content) {
        Klas klas = klasRepository.findById(classId)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found!"));
        Notification notification = new Notification();
        notification.setClasses(klas);
        notification.setSubject(subject);
        notification.setContent(content);
        notification.setRead(false);
        notification.setTimeStamp(new Date());

        return notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getUnreadNotifications() {
        return notificationRepository.findAll();
    }

    @Override
    public void markAsRead(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found!"));
        notification.setRead(true);
        notificationRepository.save(notification);
    }

    @Override
    public NotificationDto convertNotificationToDto(Notification notification) {
        return modelMapper.map(notification, NotificationDto.class);
    }

    @Override
    public List<NotificationDto> getConvertedNotifications(List<Notification> notifications) {
        return notifications.stream().map(this::convertNotificationToDto).toList();
    }
}
