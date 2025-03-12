package com.vehicle.onboard.service;

import com.vehicle.onboard.dto.VehicleRequestDto;
import com.vehicle.onboard.dto.VehicleResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VehicleService {
    VehicleResponseDto createVehicle(VehicleRequestDto requestDto);
    VehicleResponseDto getVehicleById(Long id);
    Page<VehicleResponseDto> getAllVehicles(Pageable pageable);
    VehicleResponseDto updateVehicle(Long id, VehicleRequestDto requestDto);
    void deactivateVehicle(Long id);
}
