package com.covid.webflux.service;

import com.covid.webflux.dto.CovidStatData;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class StatService {
    private WebClient webClientCovid;
    private WebClient webClientPopulation;
    private WebClient webClientAllCountriesName;
    private ObjectMapper objectMapper;

    public StatService(WebClient webClientCovid, WebClient webClientPopulation, WebClient webClientAllCountriesName, ObjectMapper objectMapper) {
        this.webClientCovid = webClientCovid;
        this.webClientPopulation = webClientPopulation;
        this.webClientAllCountriesName = webClientAllCountriesName;
        this.objectMapper = objectMapper;
    }

    public Mono<CovidStatData> getStatsCovid(){
        Mono<CovidStatData> covidStatDataMono = this.webClientCovid.get()
                .uri("/reports?region_name=benin&date=2020-04-07")
                .retrieve().bodyToMono(CovidStatData.class);



       /* covidStatDataMono.map(covidStatData -> {

            covidStatData.getData().get(0).getRegion().getName();
        }) */
        return null;
    }

    public Mono<List<String>> getAllCountries(){
       return this.webClientAllCountriesName.get()
                .retrieve().bodyToMono(JsonNode.class)
                .map(s -> s.path("body").path("countries").elements())
               .map(s -> objectMapper.convertValue(s, new TypeReference<>() {
               }));


    }
}
