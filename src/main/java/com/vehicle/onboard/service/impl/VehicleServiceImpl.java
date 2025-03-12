package com.vehicle.onboard.service.impl;

import com.vehicle.onboard.dto.VehicleRequestDto;
import com.vehicle.onboard.dto.VehicleResponseDto;
import com.vehicle.onboard.entity.Vehicle;
import com.vehicle.onboard.exception.ResourceNotFoundException;
import com.vehicle.onboard.mapper.VehicleMapper;
import com.vehicle.onboard.repository.VehicleRepository;
import com.vehicle.onboard.service.VehicleService;
import com.vehicle.onboard.service.validation.VehicleValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return vehicleMapper.toResponseDto(vehicleRepository.save(vehicle));
    }

    @Override
    @Transactional(readOnly = true)
    public VehicleResponseDto getVehicleById(Long id) {
        return vehicleMapper.toResponseDto(findVehicleById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VehicleResponseDto> getAllVehicles(Pageable pageable) {
        return vehicleRepository.findAll(pageable)
                .map(vehicleMapper::toResponseDto);
    }

    @Override
    @Transactional
    public VehicleResponseDto updateVehicle(Long id, VehicleRequestDto requestDto) {
        Vehicle vehicle = findVehicleById(id);
        validationService.validateVehicleUpdate(id, requestDto);
        vehicleMapper.updateEntity(vehicle, requestDto);
        return vehicleMapper.toResponseDto(vehicleRepository.save(vehicle));
    }

    @Override
    @Transactional
    public void deactivateVehicle(Long id) {
        Vehicle vehicle = findVehicleById(id);
        vehicle.setActive(false);
        vehicleRepository.save(vehicle);
    }

    private Vehicle findVehicleById(Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + id));
    }
}
