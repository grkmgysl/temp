package com.grkmgysl.hotelservice.repository;

import com.grkmgysl.hotelservice.model.Amenities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AmenitiesRepository extends JpaRepository<Amenities, Long> {
    Set<Amenities> findByVacationHomes_Id(Long id);

}
