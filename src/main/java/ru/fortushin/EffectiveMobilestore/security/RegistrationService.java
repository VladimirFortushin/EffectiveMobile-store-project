package ru.fortushin.EffectiveMobilestore.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fortushin.EffectiveMobilestore.dto.UserDTO;
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
    public void register(UserDTO userDTO){
        User user = new User();
        user.setAccountEnabled(true);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setUserName(userDTO.getUserName());
        user.setRole("ROLE_USER");
        user.setEmail(userDTO.getEmail());
        user.setBalance(userDTO.getBalance());
        userRepository.save(user);
    }
}
