package com.ffw.flyfarewatcher.service;

import com.ffw.flyfarewatcher.model.PriceRecord;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.*;


@Service
public class HistoryService {

    private static final String FILE_NAME= "price-history.csv";

    public  void save (int price) {
        try (FileWriter writer = new FileWriter(FILE_NAME,true)){
            String line = LocalDate.now()+","+price+ "\n";
            writer.write(line);

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public List<PriceRecord> readAll() {

        List<PriceRecord> list = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))){

            String line;
            while ((line = br.readLine())!= null){
                String[]parts = line.split(",");
                list.add(new PriceRecord(parts[0],Integer.parseInt(parts[1])));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
