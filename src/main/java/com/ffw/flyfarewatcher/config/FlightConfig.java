package com.ffw.flyfarewatcher.config;

import com.ffw.flyfarewatcher.model.Flight;
import java.util.List;

public class FlightConfig {

    public static List<Flight> flights = List.of(
            new Flight("ZAG","STN","2026-01-19"),
            new Flight("ZAG","MAD","2026-02-01"),
            new Flight("BER","TRS","2026-01-22")

    );
}