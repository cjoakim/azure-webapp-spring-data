package com.chrisjoakim.azure.sdweb.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.chrisjoakim.azure.sdweb.models.Airport;


@Repository
public interface AirportRepository extends CrudRepository<Airport, Integer> {
		
	Optional<Airport> findById(Integer id);
	
	Optional<Airport> findByIataCode(String code);
	
	List<Airport> findByCity(String city);
	
	List<Airport> findByCountry(String country);
}
