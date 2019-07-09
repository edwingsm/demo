package com.example.demo.controller;

import com.example.demo.model.JwtResponse;
import com.example.demo.model.LoginForm;
import com.example.demo.model.SignUpForm;
import com.example.demo.test.BaseIntegrationTest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AuthControllerIntegrationTest extends BaseIntegrationTest {

  private TestRestTemplate restTemplate = new TestRestTemplate();

  @Test
  public void testLoginSuccessWithCorrectCredentials() throws URISyntaxException {
    final String baseUrl = "http://localhost:" + randomServerPort + "/auth/login";
    URI uri = new URI(baseUrl);
    LoginForm user = new LoginForm();
    user.setUsername("appuser");
    user.setPassword("appus3r");

    HttpHeaders headers = new HttpHeaders();
    HttpEntity<LoginForm> request = new HttpEntity<>(user, headers);
    ResponseEntity<JwtResponse> result = this.restTemplate
        .postForEntity(uri, request, JwtResponse.class);

    Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    Assertions.assertThat(result.getBody().getType()).isEqualTo("Bearer");
    Assertions.assertThat(result.getBody().getToken()).isNotNull().isNotEmpty();
  }

  @Test
  public void testLoginFailWithInCorrectCredentials() throws URISyntaxException {
    final String baseUrl = "http://localhost:" + randomServerPort + "/auth/login";
    URI uri = new URI(baseUrl);
    LoginForm user = new LoginForm();
    user.setUsername("appuser");
    user.setPassword("password");

    HttpHeaders headers = new HttpHeaders();
    HttpEntity<LoginForm> request = new HttpEntity<>(user, headers);
    ResponseEntity<String> result = this.restTemplate
        .postForEntity(uri, request, String.class);

    Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
  }

  @Test
  public void testRegistrationForUserSuccess() throws Exception{
    final String baseUrl = "http://localhost:" + randomServerPort + "/auth/signUp";
    URI uri = new URI(baseUrl);
    final SignUpForm userForm = new SignUpForm();
    userForm.setEmail("someuser@soemdomain.com");
    userForm.setName("some user");
    userForm.setUsername("someuser");
    Set<String> userRole = new HashSet<>();
    userRole.add("USER");
    userForm.setRole(userRole);
    userForm.setPassword("appus3r");

    HttpHeaders headers = new HttpHeaders();
    HttpEntity<SignUpForm> request = new HttpEntity<>(userForm, headers);
    ResponseEntity<String> result = this.restTemplate
        .postForEntity(uri, request, String.class);
    Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  public void testRegistrationForUserFailureDueToExistingUserName() throws Exception{
    final String baseUrl = "http://localhost:" + randomServerPort + "/auth/signUp";
    URI uri = new URI(baseUrl);
    final SignUpForm userForm = new SignUpForm();
    userForm.setEmail("someuser@google.com");
    userForm.setName("some user");
    userForm.setUsername("appuser");
    Set<String> userRole = new HashSet<>();
    userRole.add("USER");
    userForm.setRole(userRole);
    userForm.setPassword("appus3r");

    HttpHeaders headers = new HttpHeaders();
    HttpEntity<SignUpForm> request = new HttpEntity<>(userForm, headers);
    ResponseEntity<String> result = this.restTemplate
        .postForEntity(uri, request, String.class);
    Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @Test
  public void testRegistrationForUserFailureDueExistingEmail() throws Exception{
    final String baseUrl = "http://localhost:" + randomServerPort + "/auth/signUp";
    URI uri = new URI(baseUrl);
    final SignUpForm userForm = new SignUpForm();
    userForm.setEmail("someuser@soemdomain.com");
    userForm.setName("some user");
    userForm.setUsername("someuser");
    Set<String> userRole = new HashSet<>();
    userRole.add("USER");
    userForm.setRole(userRole);
    userForm.setPassword("appus3r");

    HttpHeaders headers = new HttpHeaders();
    HttpEntity<SignUpForm> request = new HttpEntity<>(userForm, headers);
    ResponseEntity<String> result = this.restTemplate
        .postForEntity(uri, request, String.class);
    Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
  }


}
