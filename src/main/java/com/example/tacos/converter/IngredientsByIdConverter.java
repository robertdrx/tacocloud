package com.example.tacos.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.tacos.domain.Ingredient;
import com.example.tacos.repository.IngredientRepository;

@Component
public class IngredientsByIdConverter implements Converter<String, Ingredient> {

	private IngredientRepository ingredientRepository;
	
	public IngredientsByIdConverter(IngredientRepository ingredientRepository)
	{
		this.ingredientRepository = ingredientRepository;
	}
	
	@Override
	public Ingredient convert(String id) {
		return ingredientRepository.findById(id).orElse(null);
	}

}
