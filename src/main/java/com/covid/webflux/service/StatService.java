package com.covid.webflux.service;

import com.covid.webflux.dto.CovidStatData;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.text.Collator;
import java.util.List;
import java.util.Optional;

@Service
public class StatService {
    private WebClient webClientCovid;
    private WebClient webClientPopulation;
    private WebClient webClientAllCountriesName;
    private ObjectMapper objectMapper;
    final Collator instance = Collator.getInstance();

    public StatService(WebClient webClientCovid, WebClient webClientPopulation, WebClient webClientAllCountriesName, ObjectMapper objectMapper) {
        this.webClientCovid = webClientCovid;
        this.webClientPopulation = webClientPopulation;
        this.webClientAllCountriesName = webClientAllCountriesName;
        this.objectMapper = objectMapper;

        // This strategy mean it'll ignore the accents
        instance.setStrength(Collator.NO_DECOMPOSITION);
    }

    public Mono<CovidStatData> getStatsCovid(){
        Mono<CovidStatData> covidStatDataMono = this.webClientCovid.get()
                .uri("/reports?region_name=Cote d'Ivoire&date=2020-04-07") //benin
                .retrieve().bodyToMono(CovidStatData.class);

        Mono<List<String>> allCountries = getAllCountries();


        Mono<Optional<String>> countryFromStatsCovidAndPopulation = Mono
                .zip(covidStatDataMono, allCountries).map(tuple -> {
            String countryFromCovidData = tuple.getT1().getData().get(0).getRegion().getName();
            return tuple.getT2().stream()
                    .filter(aCountry -> instance.compare(aCountry
                            .replaceAll("\\s+","")
                            .toLowerCase(), countryFromCovidData
                            .replaceAll("\\s+","")
                            .toLowerCase()) == 0)
                    .findAny();
        });

        return Mono.zip(covidStatDataMono,countryFromStatsCovidAndPopulation).flatMap(tuple -> {
            Mono<Integer> population = getPopulation(tuple.getT2().get());
           return population.map(popu -> {
               tuple.getT1().getData().get(0).setPopulation(popu);
               return tuple.getT1();
           });
        });
    }

    public Mono<List<String>> getAllCountries(){
       return this.webClientAllCountriesName.get()
                .retrieve().bodyToMono(JsonNode.class)
                .map(s -> s.path("body").path("countries").elements())
               .map(s -> objectMapper.convertValue(s, new TypeReference<>() {
               }));


    }
    public Mono<Integer> getPopulation(String country){
        return this.webClientPopulation.get()
                .uri("?country_name="+country)
                .exchangeToMono(clientResponse -> {
                    if(clientResponse.statusCode().is2xxSuccessful()){
                        return clientResponse.bodyToMono(JsonNode.class);
                    }
                    return Mono.error(new Exception(clientResponse.statusCode().getReasonPhrase()+" "
                            +clientResponse.statusCode().value()));
                })
                .map(jsonNode -> {
                    if(jsonNode.path("body")!=null && jsonNode.path("body").path("population")!=null){
                        return Integer.valueOf(jsonNode.path("body").path("population").asText());
                    }
                    return 0;
                });

    }
}
