package com.example.tacos.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.tacos.model.Ingredient;
import com.example.tacos.model.Ingredient.Type;
import com.example.tacos.model.Taco;
import com.example.tacos.model.TacoOrder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("taccoOrder")
public class DesignTacoController {
	
	@ModelAttribute
	public void addIngredientsToModel(Model model)
	{
		log.info("Inside addIngredientsToModel");
		List<Ingredient> ingredients = Arrays.asList(
				new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
				new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
				new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
				new Ingredient("CARN", "Carnitas", Type.PROTEIN),
				new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
				new Ingredient("LETC", "Lettuce", Type.VEGGIES),
				new Ingredient("CHED", "Cheddar", Type.CHEESE),
				new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
				new Ingredient("SLSA", "Salsa", Type.SAUCE),
				new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
				);
		
		Type[] types =  Ingredient.Type.values();
		for(Type type : types) 
		{
			model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
		}
		
		System.out.println("check model");
	}

	private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
		return ingredients
				.stream()
				.filter(x -> x.getType().equals(type))
				.collect( Collectors.toList() );
	}
	
	@ModelAttribute(name = "taccoOrder")
	public TacoOrder order()
	{
		log.info("creating taccoOrder");
		return new TacoOrder();
	}
	
	@ModelAttribute(name = "taco")
	public Taco taco()
	{
		log.info("creating taco");
		return new Taco();
	}
	
	@GetMapping
	public String showDesignForm()
	{
		return "design";
	}
}
