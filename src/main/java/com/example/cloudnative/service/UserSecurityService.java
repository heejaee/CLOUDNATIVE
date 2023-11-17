package com.example.cloudnative.service;

import static com.example.cloudnative.repository.UserRole.ADMIN;
import static com.example.cloudnative.repository.UserRole.USER;

import com.example.cloudnative.domain.CloudUser;
import com.example.cloudnative.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<CloudUser> _CloudUser = this.userRepository.findByusername(username);
        if (_CloudUser.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
        }
        CloudUser CloudUser = _CloudUser.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        if ("admin".equals(username)) {
            authorities.add(new SimpleGrantedAuthority(ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(USER.getValue()));
        }
        return new User(CloudUser.getUsername(), CloudUser.getPassword(), authorities);
    }
}
