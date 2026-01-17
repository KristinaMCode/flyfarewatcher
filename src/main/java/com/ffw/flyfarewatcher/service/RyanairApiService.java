package com.ffw.flyfarewatcher.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ffw.flyfarewatcher.model.Flight;
import org.springframework.boot.autoconfigure.ssl.SslProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@Service
public class RyanairApiService {

    //RestTemplate this is object that can call REST APIs, SPring class, ki se uporablja za pošiljanje HTTP requestov, kot browser le da je v kodi
    private final RestTemplate restTemplate = new RestTemplate();

    //public String getFlights(){
    public Double getPrice(String from, String to, String date) throws Exception {
        String url = "https://www.ryanair.com/api/booking/v4/en-gb/" +
                "availability?ADT=1&TEEN=0&CHD=0&INF=0&Origin="+from+
                "&Destination="+to+"&promoCode=" +
                "&IncludeConnectingFlights=false&DateOut="+date +
                "&DateIn=&FlexDaysBeforeOut=0&FlexDaysOut=0" +
                "&FlexDaysBeforeIn=2&FlexDaysIn=2" +
                "&RoundTrip=false&IncludePrimeFares=false&ToUs=AGREED";
//pošlji GET request na ta URL in mi daj response v obliki STRINGA
    //    System.out.println("URL = " + url);
       // System.out.println("THIS IS WHERE I CALL /ryanair!");

        String response = restTemplate.getForObject(url,String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response);
        // navigate JSON

        JsonNode trips = root.path("trips");
        if(trips.isEmpty()){
            return  null;
        }

        JsonNode dates = trips.get(0).path("dates");
        if(dates.isEmpty()){
            return  null;
        }

        JsonNode flights = dates.get(0).path("flights");
       if(flights.isEmpty()){
           return  null;
       }

        JsonNode priceNode = flights.get(0)
                        .path("regularFare")
                        .path("fares")
                        .get(0)
                        .path("amount");

        return priceNode.asDouble();

        // return restTemplate.getForObject(url,String.class);
    }

    private static final String FILE = "price.txt";

    public Double readLastPrice(Flight f){
        try{
            String filename = f.getFrom()+"_"+f.getTo()+"_"+f.getDate()+".txt";
            File file = new File(filename);

            if(!file.exists()) return null;
            String content = Files.readString(file.toPath());
            return Double.parseDouble(content);
        } catch (Exception e) {
           return null;
        }
    }

    public void savePrice(Flight f,double price){
        try{
            String fileName = f.getFrom()+"_"+f.getTo()+"_"+f.getDate()+".txt";

            Files.writeString(Path.of(fileName),String.valueOf(price), StandardOpenOption.CREATE,StandardOpenOption.TRUNCATE_EXISTING);
        } catch (Exception e){
            e.printStackTrace();;
        }
    }

    public Double getPrice(Flight f) throws Exception {
        return getPrice(
                f.getFrom(),
                f.getTo(),
                f.getDate()
        );
    }

}
