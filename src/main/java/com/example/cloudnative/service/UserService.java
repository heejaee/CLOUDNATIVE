package com.example.cloudnative.service;

import com.example.cloudnative.domain.CloudUser;
import com.example.cloudnative.repository.UserRepository;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CloudUser create(String username, String email, String password) {
        CloudUser user = new CloudUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));

        userRepository.save(user);
        return user;
    }

    public CloudUser findUser(String username) {
        Optional<CloudUser> user = userRepository.findByusername(username);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new DataNotFoundException("siteuser not found");
        }
    }
}
