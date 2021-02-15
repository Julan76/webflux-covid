package com.covid.webflux.dto;

import lombok.Data;

import java.util.List;

@Data
public class CovidStatData {
    private List<CountryData> data;
}
