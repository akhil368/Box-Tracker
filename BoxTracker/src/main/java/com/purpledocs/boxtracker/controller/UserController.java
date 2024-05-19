package com.purpledocs.boxtracker.controller;

import com.purpledocs.boxtracker.dto.*;
import com.purpledocs.boxtracker.entity.User;
import com.purpledocs.boxtracker.jwt.JwtUtil;
import com.purpledocs.boxtracker.service.UserService;
import com.purpledocs.boxtracker.serviceImpl.UserDetailsService;
import com.purpledocs.boxtracker.type.UserStatus;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public UserController(UserService userService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/sign-up")
    public Response<UserDTO> saveUser(@Valid @RequestBody Request<UserDTO> request) {
        UserDTO userDTO = request.getQuery();

        Response<UserDTO> response = new Response<>();
        response.setRequestId(request.getRequestId());
        response.setTimestamp(request.getTimestamp());

        try {
            UserDTO existingUser = userService.getUserByUsername(userDTO.getUsername());
            if (existingUser != null) {
                response.setCode(ResponseCode.BAD_REQUEST);
                response.setMessage("User already exists");
                return response;
            }

            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            UserDTO savedUser = userService.saveUser(userDTO);

            response.setCode(ResponseCode.CREATED_OK);
            response.setResult(savedUser);
            response.setMessage("User saved successfully");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.setMessage(e.getMessage());
        }

        return response;
    }

    @PostMapping("/login")
    public Response<LoginResponse> login(@RequestBody Request<UserDTO> request, HttpSession session) {
        UserDTO userDTO = request.getQuery();
        Response<LoginResponse> response = new Response<>();

        response.setRequestId(request.getRequestId());
        response.setTimestamp(request.getTimestamp());

        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userDTO.getUsername());
            User user = (User) userDetails;

            if (!passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
                response.setCode(ResponseCode.BAD_REQUEST);
                response.setMessage("Invalid password, try again !");
                return response;
            }

            if (user.getUserStatus() == UserStatus.INACTIVE) {
                response.setCode(ResponseCode.FORBIDDEN);
                response.setMessage("User is inactive, login failed !");
                return response;
            }

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword()));
            final String token = jwtUtil.generateToken(userDetails);


            session.setAttribute("token", token);

            response.setCode(ResponseCode.OK);
            response.setResult(new LoginResponse(user.getUserId(), user.getUsername(), token));
            response.setMessage("Login Successfully !");

        } catch (UsernameNotFoundException e) {
            response.setCode(ResponseCode.NOT_FOUND);
            response.setMessage(e.getMessage());
        }

        return response;
    }


    @GetMapping("/get-user-by-username")
    public Response<UserDTO> getUserByUsername(@RequestParam String username) {
        UserDTO userDTO = userService.getUserByUsername(username);

        return getUserDTOResponse(userDTO);
    }

    @GetMapping("/get-user")
    public Response<UserDTO> getUserByUserId(@RequestParam Integer userId) {
        UserDTO userDTO = userService.getUserByUserId(userId);

        return getUserDTOResponse(userDTO);
    }

    @GetMapping("/get-all-users")
    public Response<List<UserDTO>> getAllUsers() {
        List<UserDTO> userDTOS = userService.getAllUsers();

        Response<List<UserDTO>> response = new Response<>();
        if (userDTOS.isEmpty()) {
            response.setCode(ResponseCode.NOT_FOUND);
            response.setMessage("No users found !");
            return response;
        }

        response.setResult(userDTOS);
        response.setCode(ResponseCode.OK);
        response.setMessage("Users fetched successfully");
        return response;
    }

    @PutMapping("/deactivate-user")
    public Response<UserDTO> deactivateUser(@RequestParam Integer userId, HttpSession session) {
        UserDTO userDTO = userService.getUserByUserId(userId);
        Response<UserDTO> response = new Response<>();

        if (userDTO != null) {
            userDTO.setUserStatus(UserStatus.INACTIVE);
            UserDTO updatedUser = userService.saveUser(userDTO);

            // Get the token associated with this user from the session
            String token = (String) session.getAttribute("token");

            if (token != null) {
                // Invalidate the user's token, Making token expired
                jwtUtil.updateTokenExpiration(token);
                log.info("Token updated successfully");
            }

            response.setCode(ResponseCode.CREATED_OK);
            response.setMessage("User account has been deactivated");
            response.setResult(updatedUser);

        } else {
            response.setCode(ResponseCode.OK);
            response.setMessage("User account not found");
        }

        return response;
    }

    @PutMapping("/activate-user")
    public Response<UserDTO> activateUser(@RequestParam Integer userId) {
        UserDTO userDTO = userService.getUserByUserId(userId);
        Response<UserDTO> response = new Response<>();

        if (userDTO != null && userDTO.getUserStatus().equals(UserStatus.INACTIVE)) {

            userDTO.setUserStatus(UserStatus.ACTIVE);
            UserDTO updatedUser = userService.saveUser(userDTO);

            response.setCode(ResponseCode.CREATED_OK);
            response.setMessage("User account has been activated");
            response.setResult(updatedUser);

        } else {
            response.setCode(ResponseCode.OK);
            response.setMessage("User account not found");
        }

        return response;
    }

    @PutMapping("/update-user")
    public Response<UserDTO> updateUser(@Valid @RequestBody Request<UserDTO> request) {
        UserDTO newUuserDTO = request.getQuery();

        Response<UserDTO> response = new Response<>();
        response.setRequestId(request.getRequestId());
        response.setTimestamp(request.getTimestamp());


        int userId = newUuserDTO.getUserId();
        if (userId == 0) {
            response.setCode(ResponseCode.BAD_REQUEST);
            response.setMessage("Please provide the userId");
            return response;
        }

        if (isUsernameDuplicate(userService.getAllUsers(), newUuserDTO)) {
            response.setCode(ResponseCode.BAD_REQUEST);
            response.setMessage("Account already exists with provided username !");
            return response;
        }

        UserDTO userDTO = userService.getUserByUserId(userId);

        try {

            userDTO.setUsername(newUuserDTO.getUsername());

            if (!newUuserDTO.getUserStatus().toString().isEmpty()) {
                userDTO.setUserStatus(newUuserDTO.getUserStatus());
            }
            if (!newUuserDTO.getPassword().isEmpty()) {
                userDTO.setPassword(passwordEncoder.encode(newUuserDTO.getPassword()));
            }

            UserDTO updatedUser = userService.saveUser(userDTO);

            response.setCode(ResponseCode.CREATED_OK);
            response.setResult(updatedUser);
            response.setMessage("User updated successfully");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.setMessage(e.getMessage());
        }
        return response;
    }


    private Response<UserDTO> getUserDTOResponse(UserDTO userDTO) {
        Response<UserDTO> response = new Response<>();

        if (userDTO != null) {
            response.setResult(userDTO);
            response.setCode(ResponseCode.OK);
            response.setMessage("User fetched successfully");
            return response;
        }

        response.setCode(ResponseCode.NOT_FOUND);
        response.setMessage("User not found");

        return response;
    }

    private boolean isUsernameDuplicate(List<UserDTO> userDTOS, UserDTO currentUser) {
        for (UserDTO userDTO : userDTOS) {
            if (userDTO.getUsername().equals(currentUser.getUsername()) && userDTO.getUserId() != currentUser.getUserId()) {
                return true;
            }
        }

        return false;
    }
}