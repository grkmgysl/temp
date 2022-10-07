package com.grkmgysl.hotelservice.dto;

import com.grkmgysl.hotelservice.model.Amenities;
import lombok.*;

import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VacationHomeResponse {

    //we created VacationHomeResponse because we don't want to expose our models to outer world
    //we can add new fields to VacationHome model but not the VacationHomeResponse
    private Long id;
    private String homeType;
    private String summary;
    private String address;
    private double price;
    private int totalOccupancy;
    private int totalBedrooms;
    private int totalBathrooms;
    private Set<Amenities> amenities;
}
