package com.grkmgysl.hotelservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grkmgysl.hotelservice.dto.VacationHomeRequest;
import com.grkmgysl.hotelservice.dto.VacationHomeResponse;
import com.grkmgysl.hotelservice.model.VacationHome;
import com.grkmgysl.hotelservice.repository.AmenitiesRepository;
import com.grkmgysl.hotelservice.repository.VacationHomeRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VacationHomeService {

    private final VacationHomeRepository vacationHomeRepository;
    private final AmenitiesRepository amenitiesRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public VacationHomeService(VacationHomeRepository vacationHomeRepository,
                               AmenitiesRepository amenitiesRepository,
                               KafkaTemplate<String, String> kafkaTemplate,
                               ObjectMapper objectMapper) {
        this.vacationHomeRepository = vacationHomeRepository;
        this.amenitiesRepository = amenitiesRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void createVacationHome(VacationHomeRequest vacationHomeRequest) throws JsonProcessingException {
        VacationHome vacationHome = mapToVacationHome(vacationHomeRequest);

        //kafka send example
        String vacationHomeString = objectMapper.writeValueAsString(vacationHomeRequest);
        kafkaTemplate.send("hotel", vacationHomeString);

        //VacationHome v = objectMapper.convertValue(s, vacationHome.getClass());

        vacationHomeRepository.save(vacationHome);
    }

    public VacationHomeResponse updateVacationHome(Long id, VacationHomeRequest vacationHomeRequest){
        Optional<VacationHome> optionalVacationHome = vacationHomeRepository.findById(id);

        if(!optionalVacationHome.isPresent()){
            throw new IllegalArgumentException("vacation home does not exist");
        }

        VacationHome vacationHome = optionalVacationHome.get();
        //do validation
        vacationHome.setHomeType(vacationHomeRequest.getHomeType());
        vacationHome.setSummary(vacationHomeRequest.getSummary());
        vacationHome.setAddress(vacationHomeRequest.getAddress());
        vacationHome.setPrice(vacationHomeRequest.getPrice());
        vacationHome.setTotalOccupancy(vacationHomeRequest.getTotalOccupancy());
        vacationHome.setTotalBedrooms(vacationHomeRequest.getTotalBedrooms());
        vacationHome.setTotalBathrooms(vacationHomeRequest.getTotalBathrooms());
        vacationHome.setAmenities(vacationHomeRequest.getAmenities());

        vacationHomeRepository.save(vacationHome);

        return mapToVacationHomeResponse(vacationHome);
    }

    public void deleteVacationHome(Long id){
        Optional<VacationHome> optionalVacationHome = vacationHomeRepository.findById(id);

        if(!optionalVacationHome.isPresent()){
            throw new IllegalArgumentException("vacation home does not exist");
        }

        VacationHome deletedVacationHome = optionalVacationHome.get();

        vacationHomeRepository.delete(deletedVacationHome);
    }

    private VacationHomeResponse mapToVacationHomeResponse(VacationHome vacationHome){

        return VacationHomeResponse.builder()
                .id(vacationHome.getId())
                .homeType(vacationHome.getHomeType())
                .summary(vacationHome.getSummary())
                .address(vacationHome.getAddress())
                .price(vacationHome.getPrice())
                .totalOccupancy(vacationHome.getTotalOccupancy())
                .totalBedrooms(vacationHome.getTotalBedrooms())
                .totalBathrooms(vacationHome.getTotalBathrooms())
                .amenities(vacationHome.getAmenities())
                .build();

    }

    private VacationHome mapToVacationHome(VacationHomeRequest vacationHomeRequest){
        return VacationHome.builder()
                .homeType(vacationHomeRequest.getHomeType())
                .summary(vacationHomeRequest.getSummary())
                .address(vacationHomeRequest.getAddress())
                .price(vacationHomeRequest.getPrice())
                .totalOccupancy(vacationHomeRequest.getTotalOccupancy())
                .totalBedrooms(vacationHomeRequest.getTotalBedrooms())
                .totalBathrooms(vacationHomeRequest.getTotalBathrooms())
                .amenities(vacationHomeRequest.getAmenities())// set access
                .build();
    }
}
