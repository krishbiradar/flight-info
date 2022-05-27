package com.virgin.flightinfo.service;

import com.virgin.flightinfo.model.FlightInfo;
import java.time.LocalDate;
import java.util.List;

public interface FlightInfoService {
    List<FlightInfo> flightsForDate(LocalDate date);
}
