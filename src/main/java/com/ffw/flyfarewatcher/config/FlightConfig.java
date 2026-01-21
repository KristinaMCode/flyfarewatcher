package com.ffw.flyfarewatcher.config;

import com.ffw.flyfarewatcher.model.Flight;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "flights")
public class FlightConfig {

    private List<Flight> list= new ArrayList<>();

    public List<Flight> getList() {
        return list;
    }

    public void setList(List<Flight> list) {
        this.list = list;
    }
}