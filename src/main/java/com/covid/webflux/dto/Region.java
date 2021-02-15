package com.covid.webflux.dto;

import lombok.Data;

@Data
public class Region {
    private String iso;
    private String name;
    private String province;
}
