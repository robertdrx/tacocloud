package com.example.tacos.repository;

import java.util.Optional;

import com.example.tacos.domain.Ingredient;

public interface IngredientRepository {
	
	Iterable<Ingredient> findAll();
	
	Optional<Ingredient> findById(String id);
	
	Ingredient save(Ingredient ingredient);
	
}
