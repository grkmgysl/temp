package com.grkmgysl.hotelservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.grkmgysl.hotelservice.dto.VacationHomeRequest;
import com.grkmgysl.hotelservice.dto.VacationHomeResponse;
import com.grkmgysl.hotelservice.service.VacationHomeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/homes")
public class VacationHomeController {

    private final VacationHomeService vacationHomeService;

    public VacationHomeController(VacationHomeService vacationHomeService) {
        this.vacationHomeService = vacationHomeService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createVacationHome(@Valid @RequestBody VacationHomeRequest vacationHomeRequest) throws JsonProcessingException {
        vacationHomeService.createVacationHome(vacationHomeRequest);
    }

    @PutMapping("{homeId}")
    @ResponseStatus(HttpStatus.OK)
    public VacationHomeResponse updateVacationHome(@PathVariable Long homeId, @RequestBody VacationHomeRequest vacationHomeRequest){
        return vacationHomeService.updateVacationHome(homeId, vacationHomeRequest);
    }

    @DeleteMapping("{homeId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteVacationHome(@PathVariable Long homeId){
        vacationHomeService.deleteVacationHome(homeId);
    }


}
