package com.betrybe.agrix.ebytr.staff.repository;

import com.betrybe.agrix.ebytr.staff.entity.PersonRemovido;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Person JPA repository.
 */
public interface PersonRemovidoRepository extends JpaRepository<PersonRemovido, Long> {

  Optional<PersonRemovido> findByUsername(String username);
}