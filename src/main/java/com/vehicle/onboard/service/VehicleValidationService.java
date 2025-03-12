package com.vehicle.onboard.service;

import com.vehicle.onboard.entity.Vehicle;
import com.vehicle.onboard.exception.DuplicateResourceException;
import com.vehicle.onboard.repository.VehicleRepository;
import com.vehicle.onboard.repository.spec.VehicleSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VehicleValidationService {

    private final VehicleRepository vehicleRepository;

    public void validateVehicleForDuplicates(String registrationNumber, String vin, Long excludeId) {
        List<Vehicle> duplicates = vehicleRepository.findAll(
            VehicleSpecification.findDuplicateVehicle(registrationNumber, vin, true)
        );

        // Filter out the current vehicle if we're updating
        duplicates = duplicates.stream()
            .filter(v -> excludeId == null || !v.getId().equals(excludeId))
            .toList();

        if (!duplicates.isEmpty()) {
            Vehicle duplicate = duplicates.get(0);
            if (duplicate.getRegistrationNumber().equals(registrationNumber)) {
                throw new DuplicateResourceException(
                    String.format("Active vehicle with registration number '%s' already exists (Vehicle ID: %d)", 
                        registrationNumber, duplicate.getId())
                );
            } else if (duplicate.getVin().equals(vin)) {
                throw new DuplicateResourceException(
                    String.format("Active vehicle with VIN '%s' already exists (Vehicle ID: %d)", 
                        vin, duplicate.getId())
                );
            }
        }
    }
}