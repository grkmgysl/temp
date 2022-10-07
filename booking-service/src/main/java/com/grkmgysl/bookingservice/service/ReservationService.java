package com.grkmgysl.bookingservice.service;

import com.grkmgysl.bookingservice.dto.ReservationRequest;
import com.grkmgysl.bookingservice.dto.ReservationResponse;
import com.grkmgysl.bookingservice.model.Reservation;
import com.grkmgysl.bookingservice.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public void createReservation(ReservationRequest reservationRequest){
        List<Reservation> reservations = reservationRepository.findByHomeId(reservationRequest.getHomeId());

        //check the reservation before saving
        checkReservation(reservations, reservationRequest.getCheckInDate(), reservationRequest.getCheckOutDate());

        Reservation newReservation = mapToReservation(reservationRequest);
        reservationRepository.save(newReservation);
    }

    private void checkReservation(List<Reservation> reservations, LocalDate checkIn, LocalDate checkOut){

        reservations.stream().forEach(reservation -> {
            if(reservation.getCheckInDate().isAfter(checkIn) && reservation.getCheckInDate().isBefore(checkOut)){
                throw new NoSuchElementException("You can't reserve at that date");
            }
            if(reservation.getCheckOutDate().isAfter(checkIn) && reservation.getCheckOutDate().isBefore(checkOut)){
                throw new NoSuchElementException("You can't reserve at that date");
            }
            if(reservation.getCheckInDate().isBefore(checkIn) && reservation.getCheckOutDate().isAfter(checkOut)){
                throw new NoSuchElementException("You can't reserve at that date");
            }
        });

    }

    private Reservation mapToReservation(ReservationRequest reservationRequest){
        return Reservation.builder()
                .totalPrice(reservationRequest.getTotalPrice())
                .guests(reservationRequest.getGuests())
                .checkInDate(reservationRequest.getCheckInDate())
                .checkOutDate(reservationRequest.getCheckOutDate())
                .userId(reservationRequest.getUserId())
                .homeId(reservationRequest.getHomeId())
                .build();
    }
}
