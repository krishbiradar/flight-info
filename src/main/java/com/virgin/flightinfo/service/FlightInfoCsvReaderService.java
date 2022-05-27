package com.virgin.flightinfo.service;

import com.virgin.flightinfo.model.ColumnNumber;
import com.virgin.flightinfo.model.FlightInfo;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FlightInfoCsvReaderService implements FlightInfoService {

    @Value("${filePath}")
    private String filePath;

    private Map<DayOfWeek, List<FlightInfo>> dailyFlights;

    private static final Map<Integer, Method> METHOD_LIST = Arrays.stream(FlightInfo.class.getMethods())
        .filter(method -> method.isAnnotationPresent(ColumnNumber.class))
        .collect(Collectors.toMap(method -> method.getAnnotation(ColumnNumber.class).value(), method -> method));

    @PostConstruct
    public void init() {
        List<FlightInfo> records = readCsv();
        this.dailyFlights = mapRecords(records);
    }


    @Override
    public List<FlightInfo> flightsForDate(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return this.dailyFlights.get(dayOfWeek);
    }

    private List<FlightInfo> readCsv() {
        List<FlightInfo> records = new ArrayList<>();
        try(Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNext()) {
                records.add(readRow(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            log.error("File {} not found", filePath, e);
        }
        return records;
    }

    private FlightInfo readRow(String row) {
        FlightInfo flightInfo = new FlightInfo();

        try (Scanner scanner = new Scanner(row)) {
            int index = 0;
            scanner.useDelimiter(",");

            while (scanner.hasNext()) {
                String value = scanner.next();
                if (!METHOD_LIST.containsKey(index)) {
                    break;
                }
                Method methodToCall = METHOD_LIST.get(index);
                methodToCall.invoke(flightInfo, value);
                index++;
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.error("Error setting values", e);
        }
        return flightInfo;
    }

    private Map<DayOfWeek, List<FlightInfo>> mapRecords(List<FlightInfo> records) {
        Map<DayOfWeek, List<FlightInfo>> map = new EnumMap<>(DayOfWeek.class);
        Arrays.asList(DayOfWeek.values()).forEach(dayOfWeek -> map.put(dayOfWeek,
            records.stream().filter(flightInfo -> flightInfo.getDaysOfWeek().contains(dayOfWeek)).collect(Collectors.toList())));
        return map;
    }

}
