package com.ffw.flyfarewatcher.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // ta class upravlja z web requesti ali y HHTP-ji
public class HealthCotroller {

    @GetMapping("/health") // kadar nekdo pokliče GET request na 'health pokliče to metodo
    public String health() {
        return "This App is running!";
    }

}
