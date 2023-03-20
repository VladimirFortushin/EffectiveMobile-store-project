package ru.fortushin.EffectiveMobilestore.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fortushin.EffectiveMobilestore.model.Notification;
import ru.fortushin.EffectiveMobilestore.repository.NotificationRepository;

import java.util.List;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public Notification get(int id) {
        return notificationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Notification with id " + id + " was not found"));
    }

    public List<Notification> getList() {
        return notificationRepository.findAll();
    }

    @Transactional
    public void create(Notification notification) {
        notificationRepository.save(notification);
    }

    @Transactional
    public void update(Notification updatedNotification, int id) {
        updatedNotification.setId(id);
        notificationRepository.save(updatedNotification);
    }

    @Transactional
    public void delete(Notification notification) {
        notificationRepository.delete(notification);
    }


}
