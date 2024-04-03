package com.betrybe.agrix.ebytr.staff.service;

import com.betrybe.agrix.ebytr.staff.entity.PersonRemovido;
import com.betrybe.agrix.ebytr.staff.exception.PersonNotFoundException;
import com.betrybe.agrix.ebytr.staff.repository.PersonRemovidoRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* Service layer class for handling persons business logic.
*/
@Service
public class PersonRemovidoService {

  private final PersonRemovidoRepository personRepository;

  @Autowired
  public PersonRemovidoService(
      PersonRemovidoRepository personRepository) {
    this.personRepository = personRepository;
  }

  /**
  * Returns a person for a given username.
  */
  public PersonRemovido getPersonByUsername(String username) {
    Optional<PersonRemovido> person = personRepository.findByUsername(username);

    if (person.isEmpty()) {
      throw new PersonNotFoundException();
    }

    return person.get();
  }

  /**
   * Creates a new person.
   */
  public PersonRemovido create(PersonRemovido person) {
    return personRepository.save(person);
  }
}