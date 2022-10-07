package com.grkmgysl.hotelservice.dto;

import com.grkmgysl.hotelservice.model.Amenities;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VacationHomeRequest {

    private String homeType;
    private String summary;
    private String address;
    private double price;
    private int totalOccupancy;
    private int totalBedrooms;
    private int totalBathrooms;
    private Set<Amenities> amenities;
}
