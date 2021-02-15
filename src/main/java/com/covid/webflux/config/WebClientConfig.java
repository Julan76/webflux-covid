package com.covid.webflux.config;

import com.covid.webflux.dto.CovidStatData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClientCovid(){
        return WebClient.builder()
                .baseUrl("https://covid-19-statistics.p.rapidapi.com")
                .defaultHeader("x-rapidapi-key", "EX5lqemI3xmshgNeLjZgiRGmXolyp10MzJ7jsnZgBdS5DVv9xH")
                .build();
    }

    @Bean
    public WebClient webClientPopulation(){
        return WebClient.builder()
                .baseUrl("https://world-population.p.rapidapi.com/population")
                .defaultHeader("x-rapidapi-key", "EX5lqemI3xmshgNeLjZgiRGmXolyp10MzJ7jsnZgBdS5DVv9xH")
                .build();
    }
    @Bean
    public WebClient webClientAllCountriesName(){
        return WebClient.builder()
                .baseUrl("https://world-population.p.rapidapi.com/allcountriesname")
                .defaultHeader("x-rapidapi-key", "EX5lqemI3xmshgNeLjZgiRGmXolyp10MzJ7jsnZgBdS5DVv9xH")
                .build();
    }

}
