package com.betrybe.agrix.controllers.dto;

import com.betrybe.agrix.models.entities.Farm;

/**
 * Farm DTO.
 */
public record FarmDto(Long id, String name, double size) {
  public Farm toEntity() {
    return new Farm(id, name, size);
  }
}