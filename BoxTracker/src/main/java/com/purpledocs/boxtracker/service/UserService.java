package com.purpledocs.boxtracker.service;

import com.purpledocs.boxtracker.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO saveUser(UserDTO userDTO);

    UserDTO getUserByUserId(int userId);

    UserDTO getUserByUsername(String username);

    List<UserDTO> getAllUsers();
}