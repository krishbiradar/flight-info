package com.virgin.flightinfo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


import com.virgin.flightinfo.model.FlightInfo;
import com.virgin.flightinfo.service.FlightInfoService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FlightInfoControllerTest {

    @Mock
    private FlightInfoService flightInfoService;

    @InjectMocks
    private FlightInfoController flightInfoController;

    @Test
    public void testGetFlightsForDate() {
        LocalDate date = LocalDate.now();
        List<FlightInfo> flightInfoList = unsortedFlights();
        List<FlightInfo> flightInfoListCopy = new ArrayList<>(flightInfoList);
        when(flightInfoService.flightsForDate(date)).thenReturn(flightInfoList);
        List<FlightInfo> sortedFlightInfoList = flightInfoController.getFlightsForDate(date);

        assertEquals(flightInfoListCopy.get(0), sortedFlightInfoList.get(2));
        assertEquals(flightInfoListCopy.get(1), sortedFlightInfoList.get(3));
        assertEquals(flightInfoListCopy.get(2), sortedFlightInfoList.get(1));
        assertEquals(flightInfoListCopy.get(3), sortedFlightInfoList.get(0));
    }

    private List<FlightInfo> unsortedFlights() {
        List<FlightInfo> flightInfoList = new ArrayList<>();
        FlightInfo flightInfoOne = new FlightInfo();
        flightInfoOne.setDepartureTime("11:00");
        flightInfoList.add(flightInfoOne);

        FlightInfo flightInfoTwo = new FlightInfo();
        flightInfoTwo.setDepartureTime("18:00");
        flightInfoList.add(flightInfoTwo);

        FlightInfo flightInfoThree = new FlightInfo();
        flightInfoThree.setDepartureTime("07:00");
        flightInfoList.add(flightInfoThree);

        FlightInfo flightInfoFour = new FlightInfo();
        flightInfoFour.setDepartureTime("01:00");
        flightInfoList.add(flightInfoFour);

        return flightInfoList;
    }
}
