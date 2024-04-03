package com.betrybe.agrix.controllers;

import com.betrybe.agrix.controllers.dto.CropDto;
import com.betrybe.agrix.models.entities.Crop;
import com.betrybe.agrix.models.entities.Fertilizer;
import com.betrybe.agrix.services.CropService;
import com.betrybe.agrix.services.FertilizerService;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
* CropController.
*/
@RestController
@RequestMapping()
public class CropController {
  private final CropService cropService;
  private final FertilizerService fertilizerService;
    
  /**
  * Constructs a new FarmController with the specified FarmService.
  */
  public CropController(CropService cropService, FertilizerService fertilizerService) {
    this.cropService = cropService;
    this.fertilizerService = fertilizerService;
  }

  /**
  * Gets all crops.
  */
  @GetMapping("/crops")
  public ResponseEntity<List<CropDto>> getAllCrops() {
    List<Crop> crops = cropService.getAllCrops();
    List<CropDto> cropDtos = CropDto.toList(crops);
    return ResponseEntity.ok(cropDtos);
  }

  /**
   * Retrieves a specific crop by its ID.
  */
  @GetMapping("/crops/{id}")
  public ResponseEntity<CropDto> getCropById(@PathVariable Long id) {
    Crop crop = cropService.getCropById(id);
    CropDto cropDto = CropDto.toDto(crop);
    return ResponseEntity.ok(cropDto);
  }

  /**
   * Search Crop by start and end date.
  */
  @GetMapping("/crops/search")
  public ResponseEntity<List<CropDto>> getCropByHarvestDate(
      @RequestParam(name = "start") LocalDate start,
      @RequestParam(name = "end") LocalDate end
  ) {
    List<Crop> crops = cropService.searchCrop(start, end);
    List<CropDto> cropDtos = CropDto.toList(crops);
    return ResponseEntity.ok(cropDtos);
  }

  /**
   * Associate Crop With Fertilizer.
  */
  @PostMapping("/crops/{cropId}/fertilizers/{fertilizerId}")
  public ResponseEntity<String> associateCropWithFertilizer(@PathVariable Long cropId,
      @PathVariable Long fertilizerId) {
    Optional<Fertilizer> fertilizerById = fertilizerService.getById(fertilizerId);
    Crop crop = cropService.getCropById(cropId);

    if (fertilizerById.isEmpty() || crop == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fertilizante não encontrado!");
    }

    Fertilizer fertilizer = fertilizerById.get();

    if (crop.getFertilizers() != null && crop.getFertilizers().equals(fertilizer)) {
      return ResponseEntity.badRequest().body("Fertilizante já associado com a plantação!");
    }

    crop.setFertilizer(fertilizer);
    cropService.createCrop(crop);
    return ResponseEntity.status(HttpStatus.CREATED)
      .body("Fertilizante e plantação associados com sucesso!");
  }
}
