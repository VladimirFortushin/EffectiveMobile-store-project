package ru.fortushin.EffectiveMobilestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fortushin.EffectiveMobilestore.model.User;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByUserName(String userName);
}