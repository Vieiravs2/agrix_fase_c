package com.betrybe.agrix.controllers.dto;

import com.betrybe.agrix.ebytr.staff.security.Role;
import com.betrybe.agrix.models.entities.Person;

/**
 * PersonDTO.
 */
public record PersonDto(String username, String password, Role role) {

  /**
  * ToResponse and FromEntity.
  */
  public static record ToResponse(Long id, String username, Role role) {
    /**
    * ToResponse and FromEntity.
    */
    public static ToResponse fromEntity(Person person) {
      return new ToResponse(
        person.getId(),
        person.getUsername(),
        person.getRole()
      );
    }
  }

  /**
  * toPerson.
  */
  public Person toPerson() {
    Person person = new Person();
    person.setUsername(username);
    person.setPassword(password);
    person.setRole(role);
    return person;
  }
}