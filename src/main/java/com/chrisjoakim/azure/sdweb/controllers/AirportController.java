/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */

package com.chrisjoakim.azure.sdweb.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chrisjoakim.azure.sdweb.dao.AirportRepository;
import com.chrisjoakim.azure.sdweb.models.Airport;


@Controller
@RequestMapping(path="/airports", produces="application/json")
public class AirportController {
	
    @Autowired
    private AirportRepository AirportRepository;

    @PostMapping
    public @ResponseBody String createAirport(@RequestBody Airport Airport) {
        AirportRepository.save(Airport);
        return String.format("Added %s", Airport);
    }

	@GetMapping("/findAll")
    public @ResponseBody Iterable<Airport> findAll() {
        return AirportRepository.findAll();
    }

	@GetMapping("/findById/{id}")
    public @ResponseBody Optional<Airport> findById(@PathVariable Integer id) {
		Optional<Airport> result = AirportRepository.findById(id);
        return result;
    }

	@GetMapping("/findByIataCode/{code}")
    public @ResponseBody Optional<Airport> findByIataCode(@PathVariable String code) {
		Optional<Airport> results = AirportRepository.findByIataCode(code);
        return results;
    }

	@GetMapping("/findByCountry/{country}")
    public @ResponseBody List<Airport> findByCountry(@PathVariable String country) {
		List<Airport> results = AirportRepository.findByCountry(country);
        return results;
    }
}
