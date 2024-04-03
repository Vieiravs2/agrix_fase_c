package com.betrybe.agrix.controllers.dto;

import com.betrybe.agrix.models.entities.Crop;
import java.time.LocalDate;
import java.util.List;

/**
 * Crop DTO.
 */
public record CropDto(Long id, String name, Double plantedArea,
    Long farmId, LocalDate plantedDate, LocalDate harvestDate) {
  
  /**
  * toDto.
  */
  public static CropDto toDto(Crop crop) {
    return new CropDto(crop.getId(), crop.getName(), crop.getPlantedArea(),
      crop.getFarm().getId(), crop.getPlantedDate(), crop.getHarvestDate());
  }

  /**
  * toEntity.
  */
  public Crop toEntity() {
    return new Crop(id, name, plantedArea, null, plantedDate, harvestDate);
  }

  /**
  * toList.
  */
  public static List<CropDto> toList(List<Crop> crops) {
    return crops.stream()
      .map(CropDto::toDto)
      .toList();
  }
}