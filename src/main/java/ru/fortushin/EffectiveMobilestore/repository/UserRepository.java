package ru.fortushin.EffectiveMobilestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fortushin.EffectiveMobilestore.model.Notification;
import ru.fortushin.EffectiveMobilestore.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByUserName(String userName);
    List<User> findAllByNotificationListIn(List<Notification> notificationList);
}
