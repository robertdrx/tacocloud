package com.example.tacos.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.tacos.domain.Ingredient;
import com.example.tacos.repository.IngredientRepository;

@Component
public class IngredientsByIdConverter implements Converter<String, Ingredient> {

	private IngredientRepository ingredientRepo;
	
	public IngredientsByIdConverter(IngredientRepository ingredientRepo)
	{
		this.ingredientRepo = ingredientRepo;
	}
	
	@Override
	public Ingredient convert(String id) {
		return ingredientRepo.findById(id).orElse(null);
	}

}
