package com.betrybe.agrix.models.repositories;

import com.betrybe.agrix.models.entities.Crop;
import com.betrybe.agrix.models.entities.Farm;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
* Crop Repository.
*/
public interface CropRepository extends JpaRepository<Crop, Long> {
  List<Crop> findByFarm(Farm farm);

  @Query(
      value = "SELECT c FROM Crop c WHERE c.harvestDate BETWEEN :start AND :end"
  )
  List<Crop> findAllByHarvestDate(LocalDate start, LocalDate end);
}