package com.betrybe.agrix.controllers;

import com.betrybe.agrix.controllers.dto.PersonDto;
import com.betrybe.agrix.models.entities.Person;
import com.betrybe.agrix.services.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class PersonController.
 */
@RestController
@RequestMapping()
public class PersonController {

  private final PersonService personService;

  public PersonController(PersonService personService) {
    this.personService = personService;
  }

  /**
  * Create a new Person.
  */
  @PostMapping("/persons")
  public ResponseEntity<PersonDto.ToResponse> createPerson(@RequestBody PersonDto personDtop) {
    Person toPerson = personDtop.toPerson();
    Person createPerson = personService.create(toPerson);
    return ResponseEntity.status(HttpStatus.CREATED).body(
      PersonDto.ToResponse.fromEntity(createPerson)
    );
  }
}