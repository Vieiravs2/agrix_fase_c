package com.betrybe.agrix.controllers;

import com.betrybe.agrix.controllers.dto.FertilizerDto;
import com.betrybe.agrix.exceptions.FarmNotFoundException;
import com.betrybe.agrix.models.entities.Fertilizer;
import com.betrybe.agrix.services.FertilizerService;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class FertilizerController.
 */
@RestController
@RequestMapping()
public class FertilizerController {

  private final FertilizerService fertilizerService;

  public FertilizerController(FertilizerService fertilizerService) {
    this.fertilizerService = fertilizerService;
  }

  /**
   * Create a new Fertilizer.
   */
  @PostMapping("/fertilizers")
  public ResponseEntity<?> createFertilizer(@RequestBody FertilizerDto fertilizerDto) {

    if (fertilizerDto == null) {
      return ResponseEntity.badRequest().body("Fertilizante DTO é nulo.");
    }

    Fertilizer fertilizer = fertilizerDto.toFertilizer();
    Fertilizer savedFertilizer = fertilizerService.createFertilizer(fertilizer);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedFertilizer);
  }

  /**
  * Get All Fertilizers.
  */
  @GetMapping("/fertilizers")
  @Secured({"ROLE_ADMIN"})
  public ResponseEntity<List<Fertilizer>> getAllFertilizers() {
    List<Fertilizer> allFertilizers = fertilizerService.getAllFertilizers();
    return ResponseEntity.ok().body(allFertilizers);
  }

  /**
  * Get Fertilizer By Id.
  */
  @GetMapping("/fertilizers/{id}")
  public ResponseEntity<?> getById(@PathVariable Long id) {
    Optional<Fertilizer> optionalFertilizer = fertilizerService.getById(id);

    Fertilizer fertilizer = optionalFertilizer.orElseGet(() -> {
      throw new FarmNotFoundException("Fertilizante não encontrado!");
    });
  
    return ResponseEntity.ok().body(fertilizer);
  }
}