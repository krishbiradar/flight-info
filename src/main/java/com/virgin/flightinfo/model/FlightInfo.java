package com.virgin.flightinfo.model;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.Setter;

@Data
public class FlightInfo {
    @Setter(onMethod_ = @ColumnNumber(0))
    private String departureTime;
    @Setter(onMethod_ = @ColumnNumber(1))
    private String destination;
    @Setter(onMethod_ = @ColumnNumber(2))
    private String destinationCode;
    @Setter(onMethod_ = @ColumnNumber(3))
    private String flightNumber;
    private List<DayOfWeek> daysOfWeek = new ArrayList<>();

    @ColumnNumber(4)
    public void setIsSunday(String value) {
        if ("x".equalsIgnoreCase(value)) {
            this.daysOfWeek.add(DayOfWeek.SUNDAY);
        }
    }

    @ColumnNumber(5)
    public void setIsMonday(String value) {
        if ("x".equalsIgnoreCase(value)) {
            this.daysOfWeek.add(DayOfWeek.MONDAY);
        }
    }

    @ColumnNumber(6)
    public void setIsTuesday(String value) {
        if ("x".equalsIgnoreCase(value)) {
            this.daysOfWeek.add(DayOfWeek.TUESDAY);
        }
    }

    @ColumnNumber(7)
    public void setIsWednesday(String value) {
        if ("x".equalsIgnoreCase(value)) {
            this.daysOfWeek.add(DayOfWeek.WEDNESDAY);
        }
    }

    @ColumnNumber(8)
    public void setIsThursday(String value) {
        if ("x".equalsIgnoreCase(value)) {
            this.daysOfWeek.add(DayOfWeek.THURSDAY);
        }
    }

    @ColumnNumber(9)
    public void setIsFriday(String value) {
        if ("x".equalsIgnoreCase(value)) {
            this.daysOfWeek.add(DayOfWeek.FRIDAY);
        }
    }

    @ColumnNumber(10)
    public void setIsSaturday(String value) {
        if ("x".equalsIgnoreCase(value)) {
            this.daysOfWeek.add(DayOfWeek.SATURDAY);
        }
    }
}
