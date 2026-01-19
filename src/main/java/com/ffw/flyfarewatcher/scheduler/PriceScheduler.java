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
import java.util.Random;



@Component //@Component = "Spring, use this class" Without @Component: Spring would IGNORE this class, Scheduler would NOT run
//@Component ="Put this class into Spring's brain"
//Spring brain = list of all important classes
//more classes can have this r+tag that Spring will create object for each clas, keep them i nmemory, manage their lyfcycle , allow them to talk to each other
public class PriceScheduler {
    @Autowired
    private EmailService emailService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private RyanairApiService ryanairApiService;
    private Integer lastPrice = null;

    //runs every 12h
    //@Scheduled(fixedRate = 12*60*60*1000) // 12h * 60 min * 60s*1000 mili s ker java time = milisekunde in 1s = 1000milisekund
  //  @Scheduled(fixedRate = 30000)
    public  void checkPrice() throws Exception {

        for (Flight f : FlightConfig.flights) {
            try{
                Double current = ryanairApiService.getPrice(
                        f.getFrom(),
                        f.getTo(),
                        f.getDate()
                );
                Double old = ryanairApiService.readLastPrice(f);
                System.out.println("Checking " + f +
                        " | Old=" + old + " New=" + current);
                if(old == null|| current <old){
                    emailService.sendMail(
                            "muzic.kristina@gmail.com",
                            "PRICE DROP!",
                            "For flight from "+f.getFrom() +" to "+f.getTo()+ " "+"Old: " + old + " €\nNew: " + current + " €"
                    );
                }
                ryanairApiService.savePrice(f,current);
            } catch (Exception e){
                System.out.println("ERROR for: " +f);
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
