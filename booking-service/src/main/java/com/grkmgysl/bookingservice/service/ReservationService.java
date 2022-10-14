package com.grkmgysl.bookingservice.service;

import com.grkmgysl.bookingservice.dto.ReservationRequest;
import com.grkmgysl.bookingservice.dto.ReservationResponse;
import com.grkmgysl.bookingservice.model.Reservation;
import com.grkmgysl.bookingservice.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final WebClient.Builder webClientBuilder;

    public ReservationService(ReservationRepository reservationRepository, WebClient.Builder webClientBuilder) {
        this.reservationRepository = reservationRepository;
        this.webClientBuilder = webClientBuilder;
    }

    public void createReservation(ReservationRequest reservationRequest){
        List<Reservation> reservations = reservationRepository.findByHomeId(reservationRequest.getHomeId());

        //check the reservation before saving
        checkReservation(reservations, reservationRequest.getCheckInDate(), reservationRequest.getCheckOutDate());

        String s = "http://hotel-service/api/homes/search/price/" + reservationRequest.getHomeId();
        //calculate the total price
        Double price = webClientBuilder.build().get()
                .uri(s)
                .retrieve()
                .bodyToMono(Double.class)
                .block();

        System.out.println(price);
        Reservation newReservation = mapToReservation(reservationRequest);

        newReservation.setTotalPrice(price * reservationRequest.getGuests());
        reservationRepository.save(newReservation);
    }

    public void deleteReservation(Long resId){
        Reservation resToDelete = optionalToReservation(resId);
        reservationRepository.delete(resToDelete);
    }

    public List<ReservationResponse> getAllReservationsByUserId(Long userId){
        List<Reservation> reservations = reservationRepository.findByUserId(userId);

        return reservations.stream().map(this::mapToReservationResponse).toList();
    }

    public List<ReservationResponse> getAllReservationsByHomeId(Long homeId){
        List<Reservation> reservations = reservationRepository.findByHomeId(homeId);

        return reservations.stream().map(this::mapToReservationResponse).toList();
    }

    private Reservation optionalToReservation(Long resId){
        Optional<Reservation> optionalReservation = reservationRepository.findById(resId);
        if(!optionalReservation.isPresent()){
            //throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            throw new NoSuchElementException("Reservation not found");
        }
        return optionalReservation.get();
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
                //.totalPrice(reservationRequest.getTotalPrice())
                .guests(reservationRequest.getGuests())
                .checkInDate(reservationRequest.getCheckInDate())
                .checkOutDate(reservationRequest.getCheckOutDate())
                .userId(reservationRequest.getUserId())
                .homeId(reservationRequest.getHomeId())
                .build();
    }

    private ReservationResponse mapToReservationResponse(Reservation reservation){
        return ReservationResponse.builder()
                .id(reservation.getId())
                .totalPrice(reservation.getTotalPrice())
                .guests(reservation.getGuests())
                .checkInDate(reservation.getCheckInDate())
                .checkOutDate(reservation.getCheckOutDate())
                .userId(reservation.getUserId())
                .homeId(reservation.getHomeId())
                .build();
    }
}
