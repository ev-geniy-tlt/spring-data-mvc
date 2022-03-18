package com.example.springdatamvc.repository;

import com.example.springdatamvc.data.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CountryRepository extends JpaRepository<Country, Integer> {

    Country findByName(String name);

    List<Country> findByNameStartingWith(String name);

    Country findByCodeName(String codeName);

    @Modifying
    @Query("update Country u set u.name = :name where u.codeName = :codeName")
    @Transactional
    void updateCountryName(@Param(value = "codeName") String codeName, @Param(value = "name") String name);
}
