package com.ffw.flyfarewatcher.controller;
import com.ffw.flyfarewatcher.model.PriceRecord;
import com.ffw.flyfarewatcher.service.HistoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HistoryController {

    private final HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping("/history")
    public List<PriceRecord> history (){
        return  historyService.readAll();
    }
}
