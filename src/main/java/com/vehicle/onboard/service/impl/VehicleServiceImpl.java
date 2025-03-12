package com.vehicle.onboard.service.impl;

import com.vehicle.onboard.dto.VehicleRequestDto;
import com.vehicle.onboard.dto.VehicleResponseDto;
import com.vehicle.onboard.entity.Vehicle;
import com.vehicle.onboard.exception.ResourceNotFoundException;
import com.vehicle.onboard.mapper.VehicleMapper;
import com.vehicle.onboard.repository.VehicleRepository;
import com.vehicle.onboard.service.VehicleService;
import com.vehicle.onboard.service.VehicleValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;
    private final VehicleValidationService validationService;

    @Override
    public VehicleResponseDto createVehicle(VehicleRequestDto requestDto) {
        // Validate for duplicates
        validationService.validateVehicleForDuplicates(
            requestDto.getRegistrationNumber(),
            requestDto.getVin(),
            null
        );

        Vehicle vehicle = vehicleMapper.toEntity(requestDto);
        vehicle.setActive(true);
        vehicle = vehicleRepository.save(vehicle);
        return vehicleMapper.toDto(vehicle);
    }

    @Override
    @Transactional(readOnly = true)
    public VehicleResponseDto getVehicleById(Long id) {
        Vehicle vehicle = findActiveVehicleById(id);
        return vehicleMapper.toDto(vehicle);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehicleResponseDto> getAllActiveVehicles() {
        return vehicleRepository.findByActiveTrue()
                .stream()
                .map(vehicleMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public VehicleResponseDto updateVehicle(Long id, VehicleRequestDto requestDto) {
        Vehicle existingVehicle = findActiveVehicleById(id);
        
        // Validate for duplicates, excluding the current vehicle
        validationService.validateVehicleForDuplicates(
            requestDto.getRegistrationNumber(),
            requestDto.getVin(),
            id
        );

        vehicleMapper.updateEntityFromDto(requestDto, existingVehicle);
        existingVehicle.setActive(true);
        existingVehicle = vehicleRepository.save(existingVehicle);
        return vehicleMapper.toDto(existingVehicle);
    }

    @Override
    public void deactivateVehicle(Long id) {
        Vehicle vehicle = findActiveVehicleById(id);
        vehicle.setActive(false);
        vehicleRepository.save(vehicle);
    }

    private Vehicle findActiveVehicleById(Long id) {
        return vehicleRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                    String.format("No active vehicle found with id: %d", id))
                );
    }
}