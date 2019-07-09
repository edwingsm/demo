package com.example.demo.test;

import com.example.demo.repository.CreditCardRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment= WebEnvironment.RANDOM_PORT)
public class BaseIntegrationTest {

  @LocalServerPort
  protected int randomServerPort;

  @Autowired
  protected RoleRepository roleRepository;

  @Autowired
  protected UserRepository userRepository;

  @Autowired
  protected CreditCardRepository creditCardRepository;



}
