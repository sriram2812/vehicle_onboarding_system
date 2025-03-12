package com.vehicle.onboard.service.impl;

import com.vehicle.onboard.dto.VehicleRequestDto;
import com.vehicle.onboard.dto.VehicleResponseDto;
import com.vehicle.onboard.entity.Vehicle;
import com.vehicle.onboard.exception.DuplicateResourceException;
import com.vehicle.onboard.exception.ResourceNotFoundException;
import com.vehicle.onboard.mapper.VehicleMapper;
import com.vehicle.onboard.repository.VehicleRepository;
import com.vehicle.onboard.service.VehicleService;
import com.vehicle.onboard.service.validation.VehicleValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;
    private final VehicleValidationService validationService;

    @Override
    @Transactional
    public VehicleResponseDto createVehicle(VehicleRequestDto requestDto) {
        validationService.validateNewVehicle(requestDto);
        
        Vehicle vehicle = vehicleMapper.toEntity(requestDto);
        vehicle = vehicleRepository.save(vehicle);
        
        return vehicleMapper.toDto(vehicle);
    }

    @Override
    @Transactional(readOnly = true)
    public VehicleResponseDto getVehicleById(Long id) {
        Vehicle vehicle = vehicleRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + id));
        
        return vehicleMapper.toDto(vehicle);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehicleResponseDto> getAllActiveVehicles() {
        return vehicleRepository.findByActiveTrue().stream()
                .map(vehicleMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public VehicleResponseDto updateVehicle(Long id, VehicleRequestDto requestDto) {
        Vehicle vehicle = vehicleRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + id));
        
        validationService.validateVehicleUpdate(id, requestDto);
        
        vehicleMapper.updateEntityFromDto(requestDto, vehicle);
        vehicle = vehicleRepository.save(vehicle);
        
        return vehicleMapper.toDto(vehicle);
    }

    @Override
    @Transactional
    public void deactivateVehicle(Long id) {
        Vehicle vehicle = vehicleRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + id));
        
        vehicle.setActive(false);
        vehicleRepository.save(vehicle);
    }
}