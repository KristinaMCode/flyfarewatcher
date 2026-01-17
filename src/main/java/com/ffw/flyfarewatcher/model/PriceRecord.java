package com.ffw.flyfarewatcher.model;

public class PriceRecord {

    private  String time;
    private int price;

    public PriceRecord (String time, int price) {
        this.time = time;
        this.price = price;
    }

    public String  getTime(){
        return  time;
    }

    public int getPrice(){
        return price;
    }
}
