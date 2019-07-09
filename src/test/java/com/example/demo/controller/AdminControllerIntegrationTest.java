package com.example.demo.controller;

import com.example.demo.security.jwt.JwtProvider;
import com.example.demo.test.BaseIntegrationTest;
import java.net.URI;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;


public class AdminControllerIntegrationTest  extends BaseIntegrationTest {


  private TestRestTemplate restTemplate = new TestRestTemplate();

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtProvider jwtProvider;

  @Test
  public void testFailWithAuthentication() throws Exception{
    //given
    final String token = generateTokenForTest("appuser","appus3r");
    final String baseUrl = "http://localhost:" + randomServerPort + "/admin/users";
    URI uri = new URI(baseUrl);
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization","Bearer "+token);
    //then
    ResponseEntity<String> result = this.restTemplate
        .exchange(uri, HttpMethod.GET, new HttpEntity<Object>(headers), String.class);
    //verify
    Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
  }

  @Test
  public void testSuccessWithAuthentication() throws Exception{
    //given
    final String token = generateTokenForTest("admin","123456");
    final String baseUrl = "http://localhost:" + randomServerPort + "/admin/users";
    URI uri = new URI(baseUrl);
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization","Bearer "+token);
    //then
    ResponseEntity<String> result = this.restTemplate
        .exchange(uri, HttpMethod.GET, new HttpEntity<Object>(headers), String.class);
    //verify
    Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
  }


  private String generateTokenForTest(String userName,String password){
    final Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            userName,
            password
        )
    );
    return jwtProvider.generateJwtToken(authentication);
  }



}
