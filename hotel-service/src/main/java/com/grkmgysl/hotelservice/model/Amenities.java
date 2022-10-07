package com.grkmgysl.hotelservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "t_amenities")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Amenities {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "amenity_id")
    private Long id;

    //amenities: 1:TV, 2:wifi, 3:kitchen, 4:airCon, 5:hangers, 6:balcony, 7:pets allowed

    @Column(name = "amenity")
    private String amenity;

    @JsonIgnore
    @ManyToMany(mappedBy = "amenities")
    private Set<VacationHome> vacationHomes = new HashSet<>();;
}
