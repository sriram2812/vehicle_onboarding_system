package com.vehicle.onboard.mapper;

import com.vehicle.onboard.dto.VehicleRequestDto;
import com.vehicle.onboard.dto.VehicleResponseDto;
import com.vehicle.onboard.entity.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface VehicleMapper {
    
    Vehicle toEntity(VehicleRequestDto requestDto);
    
    VehicleResponseDto toDto(Vehicle vehicle);
    
    void updateEntityFromDto(VehicleRequestDto requestDto, @MappingTarget Vehicle vehicle);
}