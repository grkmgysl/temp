package com.grkmgysl.hotelservice.controller;

import com.grkmgysl.hotelservice.dto.VacationHomeResponse;
import com.grkmgysl.hotelservice.service.SearchService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/homes/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<VacationHomeResponse> getAllVacationHomes(){
        return searchService.getAllVacationHomes();
    }

    @GetMapping("{homeId}")
    @ResponseStatus(HttpStatus.OK)
    public VacationHomeResponse getVacationHomeById(@PathVariable Long homeId){
        return searchService.getVacationHomeById(homeId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<VacationHomeResponse> getAllVacationHomesByType(@RequestParam String type){
        return searchService.getAllVacationHomesByType(type);
    }
}
