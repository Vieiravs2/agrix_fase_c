package com.betrybe.agrix.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

  /**
  * Returns the AuthenticationManager bean.
  */
  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    AuthenticationManager manager = authenticationConfiguration.getAuthenticationManager();
      
    if (manager != null) {
      return manager;
    }
      
    throw new IllegalStateException("Configuração incorreta!");
  }

  /**
  * Returns a BCryptPasswordEncoder bean.
  */
  @Bean
  public PasswordEncoder passwordEncoder() {
    int strength = 15;
    return new BCryptPasswordEncoder(strength);
  }
}