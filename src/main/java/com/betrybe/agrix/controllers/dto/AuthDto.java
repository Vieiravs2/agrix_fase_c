package com.betrybe.agrix.controllers.dto;

/**
* Auth DTO.
*/
public record AuthDto(String username, String password) {
  public String getUsername() {
    return username;
  } 

  public String getPassword() {
    return password;
  }
}