package com.example.springdatamvc;

import com.example.springdatamvc.data.Country;
import com.example.springdatamvc.repository.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class SpringDataMvcApplicationTests {

    @Autowired
    private CountryRepository countryRepository;

    public static final String[][] COUNTRY_INIT_DATA = {{"Australia", "AU"},
            {"Canada", "CA"}, {"France", "FR"}, {"Hong Kong", "HK"},
            {"Iceland", "IC"}, {"Japan", "JP"}, {"Nepal", "NP"},
            {"Russian Federation", "RU"}, {"Sweden", "SE"},
            {"Switzerland", "CH"}, {"United Kingdom", "GB"},
            {"United States", "US"}};

    private final List<Country> expectedCountryList = new ArrayList<>();
    private final List<Country> expectedCountryListStartsWithA = new ArrayList<>();
    private final Country countryWithChangedName = new Country(1, "RU", "Russia");

    @BeforeEach
    public void setUp() {
        initExpectedCountryLists();
    }

    @Test
    @DirtiesContext
    void contextLoads() {
        List<Country> countries = countryRepository.findAll();
        assertNotNull(countries);
        assertEquals(expectedCountryList.size(), countries.size());
        for (int i = 0; i < expectedCountryList.size(); i++) {
            assertEquals(expectedCountryList.get(i), countries.get(i));
        }
    }

    @Test
    @DirtiesContext
    public void testCountryListStartsWithA() {
        List<Country> countries = countryRepository.findByNameStartingWith("A");
        assertNotNull(countries);
        assertEquals(expectedCountryListStartsWithA.size(), countries.size());
        for (int i = 0; i < expectedCountryListStartsWithA.size(); i++) {
            assertEquals(expectedCountryListStartsWithA.get(i), countries.get(i));
        }
    }

    @Test
    @DirtiesContext
    public void testCountryChange() {
        countryRepository.updateCountryName("RU", "Russia");
        assertEquals(countryWithChangedName, countryRepository.findByCodeName("RU"));
    }

    @Test
    @DirtiesContext
    public void testGetCountryByName() {
        Country country = countryRepository.findByName("France");
        assertNotNull(country);
    }

    private void initExpectedCountryLists() {
        for (int i = 0; i < COUNTRY_INIT_DATA.length; i++) {
            String[] countryInitData = COUNTRY_INIT_DATA[i];
            Country country = new Country(i, countryInitData[1], countryInitData[0]);
            expectedCountryList.add(country);
            if (country.getName().startsWith("A")) {
                expectedCountryListStartsWithA.add(country);
            }
        }
    }
}
