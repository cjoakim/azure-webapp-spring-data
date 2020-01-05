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

import com.chrisjoakim.azure.sdweb.dao.CatRepository;
import com.chrisjoakim.azure.sdweb.models.Cat;


@Controller
@RequestMapping(path="/cats", produces="application/json")
public class CatsController {
	
    @Autowired
    private CatRepository catRepository;

    @PostMapping
    public @ResponseBody String createCat(@RequestBody Cat cat) {
        catRepository.save(cat);
        return String.format("Added %s", cat);
    }

	@GetMapping("/findAll")
    public @ResponseBody Iterable<Cat> findAll() {
        return catRepository.findAll();
    }

	@GetMapping("/findById/{id}")
    public @ResponseBody Optional<Cat> findById(@PathVariable Integer id) {
		Optional<Cat> result = catRepository.findById(id);
        return result;
    }

	@GetMapping("/findByName/{name}")
    public @ResponseBody List<Cat> findByName(@PathVariable String name) {
		List<Cat> results = catRepository.findByName(name);
        return results;
    }
	
}
