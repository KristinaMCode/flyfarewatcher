package com.ffw.flyfarewatcher.controller;


import com.ffw.flyfarewatcher.model.Flight;
import com.ffw.flyfarewatcher.service.EmailService;
import com.ffw.flyfarewatcher.service.RyanairApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Random;

@RestController
public class FlightController {

    @GetMapping("/price")
    public String price(){
       // return  "Price endpoint works. 150";
        Random random = new Random();// RANDOM is JAVA class that genarate random numbers in my case random from 0-199 and add or + 50 or from 50-249
        int price = 50 + random.nextInt(200);
        System.out.println("Current flight price " + price + " EUR"); //system out print ispis na konzolo vidi msamo jaz oz dev
        return ("Current flight price " + price + " EUR");// izpis na browserju vidi client, browser,..
    }

    @GetMapping("/exampledata")
    public String exampledata(String from, String to, String date){
        if(from == null || to == null || date == null ){
            return " Error: Missing parameter! Required: from, to, date";
        }
        if(!from.matches("[A-Z]{3}") || !to.matches("[A-Z]{3}")){
            return " Error: Invalid airport code. Use 3 uppercase letters!";
        }
        if(!date.matches("\\d{4}-\\d{2}-\\d{2}")){
            return "Error: Invalid date format! Use YYYY-MM-DD!";
        }
        LocalDate flightDate = LocalDate.parse(date);
        LocalDate now= LocalDate.now();
        if(flightDate.isBefore(now)){
            return "Error: Date cannot be in the past!";
        }
        Random random = new Random();
        int price = 50 + random.nextInt(500);
        System.out.println("Flight from: " + from + " to "+ to + " on date "+ date +" for price " + price + " EUR");
        return ("Flight from: " + from + " to "+ to + " on date "+ date +" for price " + price + " EUR");
    }

    @Autowired
    private EmailService emailService;

    @GetMapping("send-test-mail")
    public String testMail(){
        emailService.sendMail(
                "muzic.kristina@gmail.com",
                "FlyFareWatcher test",
                "Email system works! Good job :)!"
        );

        return "Email sent! Check your mail box!";
    }

    @Autowired
    private RyanairApiService ryanairApiService;
    @GetMapping("/ryanair-price")
    public String getRyanairPrice(String from, String to, String date) throws Exception {

        Flight f = new Flight(from, to, date);
        Double price =ryanairApiService.getPrice(f);
        if(price == null){
            return "No direct flights for this rout/dates!";
        }
        return "Price: " + price + " €";
    }
}
