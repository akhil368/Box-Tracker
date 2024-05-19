package com.purpledocs.boxtracker.serviceImpl;

import com.purpledocs.boxtracker.dto.UserDTO;
import com.purpledocs.boxtracker.entity.User;
import com.purpledocs.boxtracker.repository.UserRepository;
import com.purpledocs.boxtracker.service.UserService;
import com.purpledocs.boxtracker.util.ObjectMapperUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final String CLASS_NAME;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.CLASS_NAME = UserServiceImpl.class.getSimpleName();
    }

    @Override
    public UserDTO saveUser(UserDTO userDTO) {
        log.info("{}.saveUser invoked for {}", CLASS_NAME, userDTO);

        User user = ObjectMapperUtils.map(userDTO, User.class);
        User savedUser = userRepository.saveAndFlush(user);

        return ObjectMapperUtils.map(savedUser, UserDTO.class);
    }

    @Override
    public UserDTO getUserByUserId(int userId) {
        log.info("{}.getUserByUserId invoked for id: {}", CLASS_NAME, userId);

        return userRepository.findById(userId)
                .map(user -> ObjectMapperUtils.map(user, UserDTO.class))
                .orElse(null);
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        log.info("{}.getUserByUsername invoked for username: {}", CLASS_NAME, username);

        return userRepository.findUserByUsername(username)
                .map(user -> ObjectMapperUtils.map(user, UserDTO.class))
                .orElse(null);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        log.info("{}.getAllUsers invoked", CLASS_NAME);

        return ObjectMapperUtils.mapAll(userRepository.findAll(), UserDTO.class);
    }
}