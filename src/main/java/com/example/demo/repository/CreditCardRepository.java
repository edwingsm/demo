package com.example.demo.repository;

import com.example.demo.repository.dbo.CreditCard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard,Long> {

    Page<CreditCard> findByUserId(Long userId, Pageable pageable);

    Page<CreditCard> findAllByUserId(Long userId, Pageable pageable);

    Optional<CreditCard> findByIdAndUserId(Long id, Long userId);
}
