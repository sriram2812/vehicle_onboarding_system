package com.vehicle.onboard.controller;

import com.vehicle.onboard.dto.VehicleRequestDto;
import com.vehicle.onboard.dto.VehicleResponseDto;
import com.vehicle.onboard.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<VehicleResponseDto> createVehicle(@Valid @RequestBody VehicleRequestDto requestDto) {
        VehicleResponseDto responseDto = vehicleService.createVehicle(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponseDto> getVehicleById(@PathVariable Long id) {
        VehicleResponseDto responseDto = vehicleService.getVehicleById(id);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<VehicleResponseDto>> getAllActiveVehicles() {
        List<VehicleResponseDto> vehicles = vehicleService.getAllActiveVehicles();
        return ResponseEntity.ok(vehicles);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleResponseDto> updateVehicle(
            @PathVariable Long id,
            @Valid @RequestBody VehicleRequestDto requestDto) {
        VehicleResponseDto responseDto = vehicleService.updateVehicle(id, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivateVehicle(@PathVariable Long id) {
        vehicleService.deactivateVehicle(id);
        return ResponseEntity.noContent().build();
    }
}