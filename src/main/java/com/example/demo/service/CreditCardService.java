package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.UserUpdateForm;
import com.example.demo.repository.CreditCardRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.dbo.CreditCard;
import com.example.demo.repository.dbo.Role;
import com.example.demo.repository.dbo.RoleName;
import com.example.demo.repository.dbo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class CreditCardService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CreditCardRepository creditCardRepository;



    public User resetUserPassword( Long userId, UserUpdateForm updateForm) throws Exception{
        return userRepository.findById(userId).map(user -> {
            user.setPassword(updateForm.getPassword());
            return userRepository.save(user);
        }).orElseThrow(() -> new ResourceNotFoundException("userId " + userId + " not found"));
    }

    public void deleteUser( Long userId) throws Exception{
         final Optional<User> user = userRepository.findById(userId);
         user.orElseThrow(()->new ResourceNotFoundException("userId " + userId + " not found"));
         userRepository.delete(user.get());
    }

    public List<CreditCard> getAllCreditCardsByUserId(Long userId, Pageable pageable) {
        Page<CreditCard> cardPage =creditCardRepository.findAllByUserId(userId,pageable);
        return creditCardRepository.findAllByUserId(userId,pageable).getContent();
    }

    public CreditCard saveCreditCard( Long userId, CreditCard creditCard) throws Exception {
        return userRepository.findById(userId).map(user -> {
            creditCard.setUser(user);
            return creditCardRepository.save(creditCard);
        }).orElseThrow(() -> new ResourceNotFoundException("userId " + userId + " not found"));
    }

    public CreditCard updateCreditCardExpiry(Long userId, Long creditCardId,CreditCard creditCardIdRequest) throws Exception{
        return creditCardRepository.findById(creditCardId).map(creditCard -> {
            creditCard.setExpiry(creditCardIdRequest.getExpiry());
            return creditCardRepository.save(creditCard);
        }).orElseThrow(() -> new ResourceNotFoundException("creditCardId " + creditCardId + "not found"));
    }

    public void deleteCreditCard(Long userId, Long creditCardId) throws Exception{
        Optional<CreditCard> creditCard = creditCardRepository.findByIdAndUserId(creditCardId, userId);
        creditCard.orElseThrow(() -> new ResourceNotFoundException("creditCardId not found with id " + creditCardId + " and userId " + userId));
        creditCardRepository.delete(creditCard.get());
    }

}
