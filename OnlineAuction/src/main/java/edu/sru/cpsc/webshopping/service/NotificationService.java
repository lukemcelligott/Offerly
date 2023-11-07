package edu.sru.cpsc.webshopping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.sru.cpsc.webshopping.domain.misc.Notification;
import edu.sru.cpsc.webshopping.repository.misc.NotificationRepository;

import java.util.List;

@Service
public class NotificationService {
	
	@Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Transactional
    public Notification createNotification(Long userId, String message) {
        Notification notification = new Notification(userId, message);
        Notification savedNotification = notificationRepository.save(notification);
        System.out.println("Creating notification for user ID: " + userId + " with message: " + message);
        System.out.println("Notification saved with ID: " + savedNotification.getId());
        return savedNotification;
    }

    public List<Notification> getUserNotifications(Long userId) {
        return notificationRepository.findAllByUserIdAndIsReadFalse(userId);
    }

    public void markNotificationAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setIsRead(true);
        notificationRepository.save(notification);
    }
    
    public void markAllNotificationsAsReadForUser(Long userId) {
        List<Notification> notifications = notificationRepository.findAllByUserIdAndIsReadFalse(userId);
        for (Notification notification : notifications) {
            notification.setIsRead(true);
            notificationRepository.save(notification);
        }
    }
    
}