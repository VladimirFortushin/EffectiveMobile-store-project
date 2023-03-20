package ru.fortushin.EffectiveMobilestore.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fortushin.EffectiveMobilestore.model.User;
import ru.fortushin.EffectiveMobilestore.repository.UserRepository;

@Service
public class RegistrationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(User user){
        user.setAccountEnabled(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserName(user.getUserName());
        user.setRole(user.getRole());
        user.setEmail(user.getEmail());
        user.setBalance(user.getBalance());
        userRepository.save(user);
    }
}
