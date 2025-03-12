package com.vehicle.onboard.service;

import com.vehicle.onboard.dto.VehicleRequestDto;
import com.vehicle.onboard.dto.VehicleResponseDto;

import java.util.List;

public interface VehicleService {
    
    VehicleResponseDto createVehicle(VehicleRequestDto requestDto);
    
    VehicleResponseDto getVehicleById(Long id);
    
    List<VehicleResponseDto> getAllActiveVehicles();
    
    VehicleResponseDto updateVehicle(Long id, VehicleRequestDto requestDto);
    
    void deactivateVehicle(Long id);
}