package com.vehicle.onboard.mapper;

import com.vehicle.onboard.dto.VehicleRequestDto;
import com.vehicle.onboard.dto.VehicleResponseDto;
import com.vehicle.onboard.entity.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface VehicleMapper {
    Vehicle toEntity(VehicleRequestDto requestDto);
    VehicleResponseDto toResponseDto(Vehicle vehicle);
    void updateEntity(@MappingTarget Vehicle vehicle, VehicleRequestDto requestDto);
}
