package com.grkmgysl.bookingservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponse {

    private Long id;
    private Double totalPrice;
    private Integer guests;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;
    private Long userId;
    private Long homeId;
}
