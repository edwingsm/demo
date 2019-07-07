package com.example.demo.controller;

import com.example.demo.model.UserUpdateForm;
import com.example.demo.repository.dbo.CreditCard;
import com.example.demo.service.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CreditCardController {

    @Autowired
    private CreditCardService creditCardService;


    @PutMapping("/users/{userId}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<?> updateUserDetails(@PathVariable Long userId, @Valid @RequestBody UserUpdateForm updateForm) throws Exception {
        creditCardService.resetUserPassword(userId, updateForm);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/users/{userId}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) throws Exception {
        creditCardService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users/{userId}/credit-card")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<?> getAllCreditCardsByUserId(@PathVariable(value = "userId") Long userId,
                                                       Pageable pageable) {
        List<CreditCard> creditCards = creditCardService.getAllCreditCardsByUserId(userId, pageable);
        return new ResponseEntity<>(creditCards, HttpStatus.OK);
    }

    @PostMapping("/users/{userId}/credit-card")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<?> addCreditCard(@PathVariable(value = "userId") Long userId,
                                           @Valid @RequestBody CreditCard creditCard) throws Exception {
        final CreditCard card = creditCardService.saveCreditCard(userId, creditCard);
        return new ResponseEntity(card, HttpStatus.OK);
    }

    @PatchMapping("/users/{userId}/credit-card/{creditCardId}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<?> updateCreditCard(@PathVariable(value = "userId") Long userId,
                                              @PathVariable(value = "creditCardId") Long creditCardId,
                                              @Valid @RequestBody CreditCard creditCardIdRequest) throws Exception {

        CreditCard card = creditCardService.updateCreditCardExpiry(userId, creditCardId, creditCardIdRequest);
        return new ResponseEntity(card, HttpStatus.OK);
    }

    @DeleteMapping("/users/{userId}/credit-card/{creditCardId}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteCreditCard(@PathVariable(value = "userId") Long userId,
                                              @PathVariable(value = "creditCardId") Long creditCardId) throws Exception {
        creditCardService.deleteCreditCard(userId, creditCardId);
        return ResponseEntity.ok().build();
    }
}
