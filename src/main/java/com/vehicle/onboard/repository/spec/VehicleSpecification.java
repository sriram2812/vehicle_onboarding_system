package com.vehicle.onboard.repository.spec;

import com.vehicle.onboard.entity.Vehicle;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class VehicleSpecification {

    public static Specification<Vehicle> findDuplicateVehicle(String registrationNumber, String vin, Boolean active) {
        return (Root<Vehicle> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Add registration number check if provided
            if (registrationNumber != null && !registrationNumber.isEmpty()) {
                predicates.add(cb.equal(root.get("registrationNumber"), registrationNumber));
            }

            // Add VIN check if provided
            if (vin != null && !vin.isEmpty()) {
                predicates.add(cb.equal(root.get("vin"), vin));
            }

            // Add active status check if provided
            if (active != null) {
                predicates.add(cb.equal(root.get("active"), active));
            }

            // Combine predicates with OR for registration and VIN, AND for active status
            Predicate registrationOrVinPredicate = cb.or(
                predicates.stream()
                    .filter(p -> !p.equals(cb.equal(root.get("active"), active)))
                    .toArray(Predicate[]::new)
            );

            if (active != null) {
                return cb.and(registrationOrVinPredicate, cb.equal(root.get("active"), active));
            }

            return registrationOrVinPredicate;
        };
    }
}