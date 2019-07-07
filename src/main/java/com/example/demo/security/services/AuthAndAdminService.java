package com.example.demo.security.services;

import com.example.demo.exception.ResourceAlreadyExists;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.JwtResponse;
import com.example.demo.model.LoginForm;
import com.example.demo.model.SignUpForm;
import com.example.demo.model.UserUpdateForm;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.dbo.Role;
import com.example.demo.repository.dbo.RoleName;
import com.example.demo.repository.dbo.User;
import com.example.demo.security.jwt.JwtProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class AuthAndAdminService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtProvider jwtProvider;

    public JwtResponse authenticateUser(@Valid @RequestBody LoginForm loginRequest) {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String jwt = jwtProvider.generateJwtToken(authentication);
        return new JwtResponse(jwt);
    }

    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User amendUserRole( Long userId, UserUpdateForm updateForm) throws Exception{
        return userRepository.findById(userId).map(user -> {
            user.getRoles().add(new Role(RoleName.valueOf(updateForm.getRole().toUpperCase())));
            return userRepository.save(user);
        }).orElseThrow(() -> new ResourceNotFoundException("User cannot be found"));
    }

    public void registerUser(SignUpForm signUpRequest)  throws Exception{
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            log.warn("Username already exist");
            throw new ResourceAlreadyExists("Username is already taken!", new Throwable("User registration failed due to duplicate username"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            log.warn("Email already Registered");
            throw new ResourceAlreadyExists("Email is already used for registration!", new Throwable("User registration failed due to duplicate email"));
        }

        final Set<String> strRoles = signUpRequest.getRole();
        final Set<Role> roles = new HashSet<>();
        strRoles.forEach(role -> {
            switch (role) {
                case "ADMIN":
                    Role adminRole = roleRepository.findByName(RoleName.ADMIN)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                    roles.add(adminRole);

                    break;
                case "PM":
                    Role pmRole = roleRepository.findByName(RoleName.SUPER_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                    roles.add(pmRole);

                    break;
                default:
                    Role userRole = roleRepository.findByName(RoleName.USER)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                    roles.add(userRole);
            }
        });
        log.info("Roles Identified for user {}", signUpRequest.getName());
        // Creating user's account
        final User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));
        user.setRoles(roles);
        userRepository.save(user);

    }
}
