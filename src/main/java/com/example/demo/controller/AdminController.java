package com.example.demo.controller;

import com.example.demo.model.UserUpdateForm;
import com.example.demo.repository.dbo.User;
import com.example.demo.security.services.AuthAndAdminService;
import com.example.demo.service.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CreditCardService creditCardService;

    @Autowired
    private AuthAndAdminService authAndAdminService;

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<User> getAllUsers(Pageable pageable) {
        return authAndAdminService.getAllUsers(pageable);
    }


    @PatchMapping("/users/{userId}/roles")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User addUserRole(@PathVariable Long userId, @Valid @RequestBody UserUpdateForm updateForm) throws Exception{
        return authAndAdminService.amendUserRole(userId,updateForm);
    }

    @PatchMapping("/users/{userId}/password")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User resetPassword(@PathVariable Long userId, @Valid @RequestBody UserUpdateForm updateForm) throws Exception{
        return creditCardService.resetUserPassword(userId,updateForm);
    }

    @DeleteMapping("/users/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) throws Exception{
         creditCardService.deleteUser(userId);
         return ResponseEntity.ok().build();
    }

}
