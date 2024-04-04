package com.betrybe.agrix.services;

import com.betrybe.agrix.exceptions.FarmNotFoundException;
import com.betrybe.agrix.models.entities.Crop;
import com.betrybe.agrix.models.repositories.CropRepository;
import com.betrybe.agrix.models.repositories.FarmRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Service.
 */
@Service
public class CropService {
  private final CropRepository cropRepository;

  /**
   * Constructs a new CropService with the specified CropRepository.
   */
  public CropService(CropRepository cropRepository, FarmRepository farmRepository) {
    this.cropRepository = cropRepository;
  }

  /**
  * getAllCrops.
  */
  public List<Crop> getAllCrops() {
    return cropRepository.findAll();
  }

  /**
  * Creates a new crop for the specified farm and saves it to the database.
  */
  public Crop createCrop(Crop newCrop) {
    return cropRepository.save(newCrop);
  }

  /**
  * getCropById.
  */
  public Crop getCropById(Long id) {
    Optional<Crop> crop = cropRepository.findById(id);

    if (!crop.isEmpty()) {
      return crop.get();
    }

    throw new FarmNotFoundException("Plantação não encontrada!");
  }

  /**
  * searchCrop.
  */
  public List<Crop> searchCrop(LocalDate start, LocalDate end) {
    return cropRepository.findAllByHarvestDate(start, end);
  }
}