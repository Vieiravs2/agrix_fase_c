package com.betrybe.agrix.controllers;

import com.betrybe.agrix.controllers.dto.CropDto;
import com.betrybe.agrix.controllers.dto.FarmDto;
import com.betrybe.agrix.models.entities.Crop;
import com.betrybe.agrix.models.entities.Farm;
import com.betrybe.agrix.services.CropService;
import com.betrybe.agrix.services.FarmService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller responsible for handling farm-related endpoints.
 */
@RestController
@RequestMapping()
public class FarmController {

  private final FarmService farmService;

  /**
   * Constructs a new FarmController with the specified FarmService.
   */
  public FarmController(FarmService farmService) {
    this.farmService = farmService;
  }

  /**
   * Creates a new farm based on the provided FarmDto.
   */
  @PostMapping("/farms")
  public ResponseEntity<FarmDto> createFarm(@RequestBody FarmDto farmDto) {
    Farm farm = farmService.create(farmDto.toEntity());
    FarmDto createdFarmDto = new FarmDto(farm.getId(), farm.getName(), farm.getSize());
    return ResponseEntity.status(HttpStatus.CREATED).body(createdFarmDto);
  }

  /**
   * Retrieves a list of all farms saved in the database.
   */
  @GetMapping("/farms")
  public ResponseEntity<List<Farm>> getAllFarms() {
    List<Farm> allFarms = farmService.getAllFarms();
    return ResponseEntity.ok(allFarms);
  }

  /**
   * Retrieves a specific farm by its ID.
   */
  @GetMapping("/farms/{id}")
  public ResponseEntity<Farm> getFarmById(@PathVariable Long id) {
    Farm farm = farmService.getFarmById(id);
    return ResponseEntity.ok(farm);
  }

  /**
   * Maps a Crop object to a CropDto.
   */
  private CropDto mapToCropDto(Crop crop, Long farmId) {
    return new CropDto(crop.getId(), crop.getName(), crop.getPlantedArea(),
      farmId, crop.getPlantedDate(), crop.getHarvestDate());
  }

  /**
  * Creates a new crop for the farm with the specified ID.
  */
  @PostMapping("/farms/{id}/crops")
  public ResponseEntity<CropDto> createCrop(@PathVariable("id") Long farmId, 
      @RequestBody CropDto cropDto) {
    Crop createdCrop = farmService.createCrop(farmId, cropDto.toEntity());
    CropDto createdCropDto = mapToCropDto(createdCrop, farmId);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdCropDto);
  }

  /**
   * Retrieves a list of all crops farms for the farm with the specified ID.
   */
  @GetMapping("/farms/{id}/crops")
  public ResponseEntity<List<CropDto>> getAllCrops(@PathVariable("id") Long farmId) {
    List<Crop> crops = farmService.getAllCrops(farmId);
    List<CropDto> cropDtos = mapToCropDtoList(crops, farmId);
    return ResponseEntity.ok(cropDtos);
  }

  /**
  * Maps a list of Crop entities to a list of CropDto objects.
  */
  private List<CropDto> mapToCropDtoList(List<Crop> crops, Long farmId) {
    return crops.stream()
      .map(crop -> new CropDto(crop.getId(), crop.getName(), crop.getPlantedArea(),
        farmId, crop.getPlantedDate(), crop.getHarvestDate()))
      .collect(Collectors.toList());
  }
}
