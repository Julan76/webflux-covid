package com.covid.webflux.controller;

import com.covid.webflux.dto.CovidStatData;
import com.covid.webflux.service.StatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class StatController {
    private StatService statService;

    public StatController(StatService statService) {
        this.statService = statService;
    }
    @GetMapping(value = "toto")
    public Mono<CovidStatData> toto(){
        return statService.getStatsCovid();
    }
    @GetMapping(value = "tata")
    public Mono<List<String>> tata(){
            return statService.getAllCountries();
    }

}
