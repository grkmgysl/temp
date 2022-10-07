package com.grkmgysl.hotelservice.repository;

import com.grkmgysl.hotelservice.model.Amenities;
import com.grkmgysl.hotelservice.model.VacationHome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface VacationHomeRepository extends JpaRepository<VacationHome, Long> {

    List<VacationHome> findByHomeType(String homeType);

}
