package com.purpledocs.boxtracker.serviceImpl;

import com.purpledocs.boxtracker.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Inside loadUserByUsername");
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username !"));
    }
}