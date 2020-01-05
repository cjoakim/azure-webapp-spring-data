package com.chrisjoakim.azure.sdweb.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.chrisjoakim.azure.sdweb.models.Todo;


@Repository
public interface TodoRepository extends CrudRepository<Todo, Integer> {
	
	Optional<Todo> findById(Integer id);
	
	List<Todo> findByStatus(Integer status);
	
	List<Todo> findByPriority(Integer priority);
	
	List<Todo> findByName(String name);
	
}
