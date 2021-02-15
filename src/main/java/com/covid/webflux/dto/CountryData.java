package com.covid.webflux.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CountryData {
    private LocalDate date;
    private Integer confirmed;
    private Integer deaths;
    private Integer recovered;
    @JsonProperty("confirmed_diff")
    private Integer confirmedDiff;
    @JsonProperty("deaths_diff")
    private Integer deathsDiff;
    @JsonProperty("recovered_diff")
    private Integer recoveredDiff;
    @JsonProperty("last_update")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdate;
    private Integer active;
    @JsonProperty("active_diff")
    private Integer activeDiff;
    @JsonProperty("fatality_rate")
    private BigDecimal fatalityRate;
    private Region region;
    private Integer population;
}
