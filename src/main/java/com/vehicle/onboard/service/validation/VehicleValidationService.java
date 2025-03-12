package com.vehicle.onboard.service.validation;

import com.vehicle.onboard.dto.VehicleRequestDto;
import com.vehicle.onboard.entity.Vehicle;
import com.vehicle.onboard.exception.DuplicateResourceException;
import com.vehicle.onboard.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VehicleValidationService {

    private final VehicleRepository vehicleRepository;

    public void validateNewVehicle(VehicleRequestDto requestDto) {
        validateRegistrationNumber(requestDto.getRegistrationNumber(), null);
        validateVin(requestDto.getVin(), null);
    }

    public void validateVehicleUpdate(Long id, VehicleRequestDto requestDto) {
        validateRegistrationNumber(requestDto.getRegistrationNumber(), id);
        validateVin(requestDto.getVin(), id);
    }

    private void validateRegistrationNumber(String registrationNumber, Long excludeId) {
        Optional<Vehicle> existingVehicle = vehicleRepository.findByRegistrationNumberAndActiveTrue(registrationNumber);
        if (existingVehicle.isPresent() && (excludeId == null || !existingVehicle.get().getId().equals(excludeId))) {
            throw new DuplicateResourceException("Vehicle with registration number " + registrationNumber + " already exists");
        }
    }

    private void validateVin(String vin, Long excludeId) {
        Optional<Vehicle> existingVehicle = vehicleRepository.findByVinAndActiveTrue(vin);
        if (existingVehicle.isPresent() && (excludeId == null || !existingVehicle.get().getId().equals(excludeId))) {
            throw new DuplicateResourceException("Vehicle with VIN " + vin + " already exists");
        }
    }
}
