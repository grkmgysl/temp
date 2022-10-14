package com.grkmgysl.hotelservice.service;

import com.grkmgysl.hotelservice.dto.VacationHomeResponse;
import com.grkmgysl.hotelservice.model.VacationHome;
import com.grkmgysl.hotelservice.repository.VacationHomeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SearchService {

    private final VacationHomeRepository vacationHomeRepository;

    public SearchService(VacationHomeRepository vacationHomeRepository) {
        this.vacationHomeRepository = vacationHomeRepository;
    }

    public List<VacationHomeResponse> getAllVacationHomes(){

        List<VacationHome> vacationHomes = vacationHomeRepository.findAll();
        return vacationHomes.stream().map(this::mapToVacationHomeResponse).toList();
    }

    public VacationHomeResponse getVacationHomeById(Long homeId){

        VacationHome vacationHome = optionalToVacationHome(homeId);
        return mapToVacationHomeResponse(vacationHome);
    }

    public List<VacationHomeResponse> getAllVacationHomesByType(String type){
        List<VacationHome> vacationHomes = vacationHomeRepository.findByHomeType(type);
        return vacationHomes.stream().map(this::mapToVacationHomeResponse).toList();
    }

    public Double getHomePrice(Long homeId){
        VacationHome vacationHome = optionalToVacationHome(homeId);
        return vacationHome.getPrice();
    }

    private VacationHome optionalToVacationHome(Long homeId){

        Optional<VacationHome> optionalVacationHome = vacationHomeRepository.findById(homeId);
        if(!optionalVacationHome.isPresent()){
            throw new IllegalArgumentException("vacation home does not exist");
        }
        return optionalVacationHome.get();
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
}
