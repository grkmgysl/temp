package com.grkmgysl.bookingservice.controller;

import com.grkmgysl.bookingservice.dto.ReservationRequest;
import com.grkmgysl.bookingservice.dto.ReservationResponse;
import com.grkmgysl.bookingservice.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createReservation(@RequestBody ReservationRequest reservationRequest){
        reservationService.createReservation(reservationRequest);
    }
}
