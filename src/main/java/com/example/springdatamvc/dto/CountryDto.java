package com.example.springdatamvc.dto;

import com.example.springdatamvc.data.Country;

public class CountryDto {

    private int id;
    private String codeName;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CountryDto() {
    }

    public CountryDto(int id, String codeName, String name) {
        this.id = id;
        this.codeName = codeName;
        this.name = name;
    }

    public static CountryDto toDto(Country country) {
        return new CountryDto(country.getId(), country.getCodeName(), country.getName());
    }
}
