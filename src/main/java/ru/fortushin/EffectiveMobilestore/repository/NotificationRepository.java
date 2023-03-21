package ru.fortushin.EffectiveMobilestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.fortushin.EffectiveMobilestore.model.Notification;
import ru.fortushin.EffectiveMobilestore.model.User;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    @Query("SELECT u.notificationList FROM User u WHERE u.id = :userId")
    List<Notification> findAllByUserId(int userId);
}
