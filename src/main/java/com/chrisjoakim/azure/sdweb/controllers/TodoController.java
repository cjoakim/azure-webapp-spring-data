package com.chrisjoakim.azure.sdweb.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chrisjoakim.azure.sdweb.dao.TodoRepository;
import com.chrisjoakim.azure.sdweb.models.Todo;



@Controller
@RequestMapping(path="/todo", produces="application/json")
public class TodoController {
	
    @Autowired
    private TodoRepository repository;

    @PostMapping
    public @ResponseBody String createTodo(@RequestBody Todo t) {
    	repository.save(t);
        return String.format("Added %s", t);
    }

	@GetMapping("/findAll")
    public @ResponseBody Iterable<Todo> findAll() {
        return repository.findAll();
    }

	@GetMapping("/findById/{id}")
    public @ResponseBody Optional<Todo> findById(@PathVariable Integer id) {
		Optional<Todo> result = repository.findById(id);
        return result;
    }

	@GetMapping("/findByName/{name}")
    public @ResponseBody List<Todo> findByName(@PathVariable String name) {
		List<Todo> results = repository.findByName(name);
        return results;
    }
	
	@GetMapping("/findByStatus/{status}")
    public @ResponseBody List<Todo> findByStatus(@PathVariable Integer status) {
		List<Todo> results = repository.findByStatus(status);
        return results;
	}
	
	@GetMapping("/findByPriority/{priority}")
    public @ResponseBody List<Todo> findByPriority(@PathVariable Integer priority) {
		List<Todo> results = repository.findByPriority(priority);
        return results;
    }
	
	@GetMapping("/setCompleted/{id}")
    public ResponseEntity<?> setStatusCompleted(@PathVariable Integer id) {
		Optional<Todo> result = repository.findById(id);
		HttpHeaders headers = new HttpHeaders();
		if (result.isPresent()) {
			Todo t = result.get();
			t.setStatusCompleted();
			repository.save(t);
			return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT); // NO_CONTENT = 204
			// https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/HttpStatus.html#NO_CONTENT
		}
		else {
			return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
		}
    }
	
	@GetMapping("/setPriority/{id}/{priority}")
    public ResponseEntity<?> setPriority(@PathVariable Integer id, @PathVariable Integer priority) {
		Optional<Todo> result = repository.findById(id);
		HttpHeaders headers = new HttpHeaders();
		if (result.isPresent()) {
			Todo t = result.get();
			t.setPriority(priority);
			repository.save(t);
			return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT); // NO_CONTENT = 204
			// https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/HttpStatus.html#NO_CONTENT
		}
		else {
			return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
		}
    }
	
}
