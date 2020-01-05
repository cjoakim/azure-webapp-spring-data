package com.chrisjoakim.azure.sdweb.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.chrisjoakim.azure.sdweb.models.Cat;


@Repository
public interface CatRepository extends CrudRepository<Cat, Integer> {
		
	Optional<Cat> findById(Integer id);
	
	List<Cat> findByName(String name);
}
