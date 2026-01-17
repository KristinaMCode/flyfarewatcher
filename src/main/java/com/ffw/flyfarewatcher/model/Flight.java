package com.ffw.flyfarewatcher.model;

import javax.print.attribute.DateTimeSyntax;

public class Flight {
    private String from;
    private String to;
    private String date;

    //Constructor
    public Flight(String from, String to, String date){
        this.from = from;
        this.to = to;
        this.date=date;
    }

    public String getFrom(){
        return  from;
    }
    public  String getTo(){
        return  to;
    }
    public  String getDate(){
        return  date;
    }
    @Override
    public String toString(){
        return from + " ->" + to + " ("+date+")";
    }
}
