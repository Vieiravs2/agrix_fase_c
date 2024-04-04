package com.betrybe.agrix.controllers;

import com.betrybe.agrix.controllers.dto.AuthDto;
import com.betrybe.agrix.controllers.dto.AuthResponseDto;
import com.betrybe.agrix.models.entities.Person;
import com.betrybe.agrix.security.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Auth Controller.
 */
@RestController
@RequestMapping()
public class AuthController {
  private final AuthenticationManager authenticationManager;
  private final TokenService tokenService;

  /**
   * Constructor.
   */
  public AuthController(AuthenticationManager authenticationManager, 
      TokenService tokenService) {
    this.authenticationManager = authenticationManager;
    this.tokenService = tokenService;
  }

  /**
   * Login Auth.
   */
  @PostMapping("/auth/login")
  public ResponseEntity<AuthResponseDto> login(@RequestBody AuthDto authenticationDto) {
    UsernamePasswordAuthenticationToken authenticationToken = 
        createAuthenticationToken(authenticationDto);
    Authentication authentication = authenticateUser(authenticationToken);

    if (authentication.isAuthenticated()) {
      Person authenticatedPerson = getAuthenticatedPerson(authentication);
      String token = generateJwtToken(authenticatedPerson);
      AuthResponseDto responseDto = new AuthResponseDto(token);
      return ResponseEntity.ok(responseDto);
    }

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }

  private UsernamePasswordAuthenticationToken createAuthenticationToken(AuthDto authenticationDto) {
    return new UsernamePasswordAuthenticationToken(
        authenticationDto.getUsername(),
        authenticationDto.getPassword()
    );
  }

  private Authentication authenticateUser(UsernamePasswordAuthenticationToken authenticationToken) {
    return authenticationManager.authenticate(authenticationToken);
  }

  private Person getAuthenticatedPerson(Authentication authentication) {
    return (Person) authentication.getPrincipal();
  }

  private String generateJwtToken(Person person) {
    return tokenService.generateToken(person);
  }

}