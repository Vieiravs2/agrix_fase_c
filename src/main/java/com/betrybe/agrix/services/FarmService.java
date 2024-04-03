package com.betrybe.agrix.services;

import com.betrybe.agrix.exceptions.FarmNotFoundException;
import com.betrybe.agrix.models.entities.Crop;
import com.betrybe.agrix.models.entities.Farm;
import com.betrybe.agrix.models.repositories.CropRepository;
import com.betrybe.agrix.models.repositories.FarmRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service responsible for handling farm-related operations.
 */
@Service
public class FarmService {

  private final FarmRepository farmRepository;
  private final CropRepository cropRepository;

  /**
   * Constructs a new FarmService with the specified FarmRepository.
   */
  public FarmService(FarmRepository farmRepository, CropRepository cropRepository) {
    this.farmRepository = farmRepository;
    this.cropRepository = cropRepository;
  }

  /**
   * Creates a new farm and saves it to the database.
   */
  @Transactional
  public Farm create(Farm farm) {
    return farmRepository.save(farm);
  }

  /**
   * Retrieves a list of all farms saved in the database.
   */
  public List<Farm> getAllFarms() {
    return farmRepository.findAll();
  }

  /**
  * Retrieves a specific farm by its ID.
  */
  public Farm getFarmById(Long id) {
    Optional<Farm> farm = farmRepository.findById(id);

    if (!farm.isEmpty()) {
      return farm.get();
    }

    throw new FarmNotFoundException("Fazenda não encontrada!");
  }

  /**
  * Retrieves the farm with the specified ID.
  */
  private Farm retrieveFarmById(Long farmId) {
    Optional<Farm> optionalFarm = farmRepository.findById(farmId);

    if (optionalFarm.isEmpty()) {
      throw new FarmNotFoundException("Fazenda não encontrada!");
    }

    return optionalFarm.get();
  }

  /**
  * Creates a new crop for the specified farm and saves it to the database.
  */
  public Crop createCrop(Long farmId, Crop newCrop) {
    Farm farm = retrieveFarmById(farmId);
    newCrop.setFarm(farm);
    return cropRepository.save(newCrop);
  }
  
  public List<Crop> getAllCrops(Long farmId) {
    Farm farm = getFarmById(farmId);
    return cropRepository.findByFarm(farm);
  }
}
