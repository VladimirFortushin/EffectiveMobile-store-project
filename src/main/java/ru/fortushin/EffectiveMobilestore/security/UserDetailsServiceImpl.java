package ru.fortushin.EffectiveMobilestore.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.fortushin.EffectiveMobilestore.model.User;
import ru.fortushin.EffectiveMobilestore.repository.UserRepository;

import java.util.Optional;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
@Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userToBeFound = userRepository.findUserByUserName(username);
        if(!userToBeFound.isPresent()){
            throw new UsernameNotFoundException(String.format("User with %s username was not found", username));
        }
        return null;
    }
}
