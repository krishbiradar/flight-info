package com.virgin.flightinfo.controller;

import com.virgin.flightinfo.model.FlightInfo;
import com.virgin.flightinfo.service.FlightInfoService;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FlightInfoController {

    private final FlightInfoService flightInfoService;

    public FlightInfoController(FlightInfoService flightInfoService) {
        this.flightInfoService = flightInfoService;
    }

    @GetMapping("/flights/{date}")
    public List<FlightInfo> getFlightsForDate(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        List<FlightInfo> flights = flightInfoService.flightsForDate(date);
        flights.sort(Comparator.comparing(FlightInfo::getDepartureTime));
        return flights;
    }
}
