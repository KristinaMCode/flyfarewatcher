package com.ffw.flyfarewatcher.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ffw.flyfarewatcher.config.FlightConfig;
import com.ffw.flyfarewatcher.model.Flight;
import com.ffw.flyfarewatcher.service.EmailService;
import com.ffw.flyfarewatcher.service.HistoryService;
import com.ffw.flyfarewatcher.service.RyanairApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;


@Component
//@Component = "Spring, use this class" Without @Component: Spring would IGNORE this class, Scheduler would NOT run
//@Component ="Put this class into Spring's brain"
//Spring brain = list of all important classes
//more classes can have this r+tag that Spring will create object for each clas, keep them i nmemory, manage their lyfcycle , allow them to talk to each other
public class PriceScheduler {
    @Autowired
    private EmailService emailService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private FlightConfig flightConfig;

    @Autowired
    private RyanairApiService ryanairApiService;
    private Integer lastPrice = null;

    @Scheduled(fixedRate = 30000)
    public void checkPrice() throws Exception {

        for (Flight f : flightConfig.getList()) {
            try {
                Double current = ryanairApiService.getPrice(
                        f.getFrom(),
                        f.getTo(),
                        f.getDate()
                );

                List<Flight> flights = flightConfig.getList();
               LocalDate dateOfFly = LocalDate.parse(f.getDate());
                LocalDate today = LocalDate.now();
                if(dateOfFly.isBefore(today)){
                    System.out.println("Flight " + f.getFrom() + " -> " + f.getTo() +
                            " on " + f.getDate() + " IS OUT OF DATE!"
                    );
                    continue;
                }
                if (flights == null || flights.isEmpty()) {
                    System.out.println("No flights configured!");
                    return;
                }
                if (current == null) {
                    System.out.println("Flight " + f.getFrom() + " -> " + f.getTo() +
                            " on " + f.getDate() + " NOT FOUND"
                    );
                    continue;
                }
                if (current == 0) {
                    System.out.println("Price is zero – probably invalid flight");
                    continue;
                }

                Double old = ryanairApiService.readLastPrice(f);
                System.out.println("Checking " + f +
                        " | Old=" + old + " New=" + current);
                String html =
                        "<h2>✈️ PRICE DROP!</h2>" +
                                "<p><b>Flight:</b>" + f.getFrom() + " -> " + f.getTo() + "</p>" +
                                "<p><b>Old price:</b>" + old + "</p>" +
                                "<p><b>New price:</b>" + current + "</p>" +
                                "<p><b>\uD83D\uDD25 Book now!</b></p>";

               if (old == null || current < old) {
                //if ( current <= old) {
                    emailService.sendHtmlMail(
                            "muzic.kristina@gmail.com",
                            "PRICE DROP!",
                            html
                    );
                }
                ryanairApiService.savePrice(f, current);
            } catch (Exception e) {
                System.out.println("ERROR for: " + f);
            }
        }


//        String from = "LJU";
//        String to = "VIE";
//        String date = LocalDate.now().plusDays(10).toString();
//
//        Random random = new Random();
//        int price = 50 + random.nextInt(20);
//        historyService.save(price);
//        System.out.println("AUTO CHECK price: " + price + " EUR");
//
//        if(lastPrice==null){
//            lastPrice= price;
//            System.out.println(("First run, no email sent!"));
//            return;
//        }
//
//        if(price<lastPrice){
//            emailService.sendMail(
//                    "muzic.kristina@gmail.com",
//                    "🔥 PRICE DROP!",
//                    "Price dropped from " + lastPrice + " EUR to " + price + " EUR"
//            );
//            System.out.println("EMAIL SENT - price dropped!");
//        } else{
//            System.out.println("No drop – email not sent");
//        }
//
//        lastPrice = price;
//        historyService.save(price);
////        emailService.sendMail(
////                "muzic.kristina@gmail.com",
////                "Flight price update for flight from " +from,
////                "Price is: " + price + " EUR"
////        );
//
//        System.out.println("AUTO CHECK " + from + " -->" + to + " on " + date +
//         " is at price " + price + "EUR");
    }
}
