package com.betrybe.agrix.services;

import com.betrybe.agrix.ebytr.staff.exception.PersonNotFoundException;
import com.betrybe.agrix.exceptions.FarmNotFoundException;
import com.betrybe.agrix.models.entities.Person;
import com.betrybe.agrix.models.repositories.PersonRepository;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service for Persons.
 */
@Service
public class PersonService implements UserDetailsService {

  private final PersonRepository personRepository;

  public PersonService(
      PersonRepository personRepository, PasswordEncoder passwordEncoder) {
    this.personRepository = personRepository;
  }

  /**
  * Inserts a new person.
  */
  public Person createPerson(Person person) {
    String encryptedPassword = new BCryptPasswordEncoder().encode(person.getPassword());
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    if (encoder.matches(person.getPassword(), encryptedPassword)) {
      person.setPassword(encryptedPassword);
    }

    return personRepository.save(person);
  }

  /**
  * Retrieves a specific person by its ID.
  */
  public Person getPersonById(Long id) {
    Optional<Person> person = personRepository.findById(id);

    if (!person.isEmpty()) {
      return person.get();
    }
    
    throw new PersonNotFoundException();
  }

  /**
  * Loads user by username.
  */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    if (username == null || username.isEmpty()) {
      throw new PersonNotFoundException();
    }

    return personRepository.findByUsername(username);
  }
}
