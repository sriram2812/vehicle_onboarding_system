package com.vehicle.onboard.repository;

import com.vehicle.onboard.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long>, JpaSpecificationExecutor<Vehicle> {
    List<Vehicle> findByActiveTrue();
    Optional<Vehicle> findByIdAndActiveTrue(Long id);
    boolean existsByRegistrationNumberAndActiveTrue(String registrationNumber);
    boolean existsByVinAndActiveTrue(String vin);
}