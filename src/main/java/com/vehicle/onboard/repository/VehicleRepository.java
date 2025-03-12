package com.vehicle.onboard.repository;

import com.vehicle.onboard.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long>, JpaSpecificationExecutor<Vehicle> {
    Optional<Vehicle> findByRegistrationNumberAndActiveTrue(String registrationNumber);
    Optional<Vehicle> findByVinAndActiveTrue(String vin);
}
