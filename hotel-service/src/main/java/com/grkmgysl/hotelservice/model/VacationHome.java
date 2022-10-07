package com.grkmgysl.hotelservice.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "t_vacation_home")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VacationHome {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "home_id")
    private Long id;

    @Column(name = "home_type")
    private String homeType;

    @Column(name = "home_summary")
    private String summary;

    @Column(name = "home_address")
    private String address;

    @Column(name = "home_price")
    private double price;

    @Column(name = "home_total_occupancy")
    private int totalOccupancy;

    @Column(name = "home_total_bedrooms")
    private int totalBedrooms;

    @Column(name = "home_total_bathrooms")
    private int totalBathrooms;


    @ManyToMany
    @JoinTable(name = "vacation_home_amenities",
            joinColumns = @JoinColumn(name = "home_id"),
            inverseJoinColumns = @JoinColumn(name = "amenity_id"))
    private Set<Amenities> amenities = new HashSet<>();

}
