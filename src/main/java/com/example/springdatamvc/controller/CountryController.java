package com.example.springdatamvc.controller;

import com.example.springdatamvc.data.Country;
import com.example.springdatamvc.dto.CountryDto;
import com.example.springdatamvc.exception.NotFoundException;
import com.example.springdatamvc.repository.CountryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CountryController {

    private final CountryRepository countryRepository;

    public CountryController(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @GetMapping("/countries")
    public List<CountryDto> getCountries() {
        return countryRepository.findAll().stream().map(CountryDto::toDto).collect(Collectors.toList());
    }

    @GetMapping("/country/{id}")
    public CountryDto getCountry(@PathVariable("id") int id) {
        return countryRepository.findById(id).map(CountryDto::toDto).orElseThrow(NotFoundException::new);
    }

    @PostMapping("/country")
    public CountryDto create(@RequestBody CountryDto countryDto) {
        Country country = countryRepository.save(new Country(countryDto.getCodeName(), countryDto.getName()));
        return CountryDto.toDto(country);
    }

    @PutMapping("/country")
    public void update(@RequestParam("codeName") String codeName, @RequestParam("newName") String newName) {
        countryRepository.updateCountryName(codeName, newName);
    }

    @DeleteMapping("/country/{id}")
    public void delete(@PathVariable("id") int id) {
        Country country = countryRepository.findById(id).orElseThrow(NotFoundException::new);
        countryRepository.delete(country);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotEnoughFunds(NotFoundException ex) {
        return ResponseEntity.badRequest().body("Not found");
    }
}
