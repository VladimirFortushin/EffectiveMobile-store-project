package ru.fortushin.EffectiveMobilestore.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fortushin.EffectiveMobilestore.model.User;
import ru.fortushin.EffectiveMobilestore.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User get(int id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with id " + id + "was not found"));
    }

    @Transactional
    public void create(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void update(User updatedUser, int id) {
        updatedUser.setId(id);
        userRepository.save(updatedUser);
    }

    @Transactional
    public void delete(User user){
        userRepository.delete(user);
    }


}
