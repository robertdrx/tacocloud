package com.example.tacos.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.tacos.domain.Ingredient;
import com.example.tacos.domain.Ingredient.Type;
import com.example.tacos.domain.Taco;
import com.example.tacos.domain.TacoOrder;
import com.example.tacos.repository.IngredientRepository;
import com.example.tacos.repository.TacoRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
@RequiredArgsConstructor
public class DesignTacoController {
	
	private final IngredientRepository ingredientRepo;
	private final TacoRepository tacoRepo;
	
	@ModelAttribute
	public void addIngredientsToModel(Model model)
	{
		log.info("Inside addIngredientsToModel");
		
		List<Ingredient> ingredients = new ArrayList<>();
		
		ingredientRepo.findAll().forEach( i -> ingredients.add(i));
		
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
	
	@ModelAttribute(name = "tacoOrder")
	public TacoOrder order()
	{
		log.info("creating tacoOrder");
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
	
	@PostMapping
	public String processTaco(@Valid Taco taco, Errors errors, @ModelAttribute TacoOrder tacoOrder)
	{
		if(errors.hasErrors())
		{
			return "design";
		}
		Taco tacoSaved = tacoRepo.save(taco);
		tacoOrder.addTaco(tacoSaved);
		log.info("Processing taco: {}", taco);
		
		return "redirect:/orders/current";
	}
}
