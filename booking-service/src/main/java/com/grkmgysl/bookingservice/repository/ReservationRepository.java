package com.grkmgysl.bookingservice.repository;

import com.grkmgysl.bookingservice.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByHomeId(Long homeId);

}
