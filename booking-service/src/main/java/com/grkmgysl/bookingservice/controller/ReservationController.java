package com.grkmgysl.bookingservice.controller;

import com.grkmgysl.bookingservice.dto.ReservationRequest;
import com.grkmgysl.bookingservice.dto.ReservationResponse;
import com.grkmgysl.bookingservice.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createReservation(@RequestBody ReservationRequest reservationRequest){
        reservationService.createReservation(reservationRequest);
    }

    @DeleteMapping("/delete/{resId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteReservation(@PathVariable Long resId){
        reservationService.deleteReservation(resId);
    }

    @GetMapping("/user/{userId}")
    public List<ReservationResponse> getAllReservationsByUserId(@PathVariable Long userId){
        return reservationService.getAllReservationsByUserId(userId);
    }

    @GetMapping("/home/{homeId}")
    public List<ReservationResponse> getAllReservationsByHomeId(@PathVariable Long homeId){
        return reservationService.getAllReservationsByHomeId(homeId);
    }
}
