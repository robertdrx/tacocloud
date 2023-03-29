package com.example.tacos.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.tacos.domain.Ingredient;

@Repository
public interface IngredientRepository extends CrudRepository<Ingredient, String> {

}
