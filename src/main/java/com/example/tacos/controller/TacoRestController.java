package com.example.tacos.controller;

import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.tacos.domain.Taco;
import com.example.tacos.domain.TacoOrder;
import com.example.tacos.repository.OrderRepository;
import com.example.tacos.repository.TacoRepository;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/api/tacos",
				produces="application/json")
@CrossOrigin(origins="http//localhost/9080/api/tacos")
@AllArgsConstructor
public class TacoRestController {
	
	private TacoRepository tacoRepo;
	
	private OrderRepository orderRepo;
	
	@GetMapping(params="recent")
	public Iterable<Taco> recentTacos()
	{
		PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
		return tacoRepo.findAll(page).getContent();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Taco> findById(@PathVariable("id") Long id)
	{
		Optional<Taco> opt = tacoRepo.findById(id);
		
		if(opt.isPresent())
		{
			return new ResponseEntity<>(opt.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
	
	@PostMapping(consumes="application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Taco postTaco(@RequestBody Taco taco)
	{
		return tacoRepo.save(taco);
	}
	
	@PatchMapping(path="/{orderId}", consumes="application/json")
	public TacoOrder patchOrder(@PathVariable("orderId") Long orderId, 
			@RequestBody TacoOrder patch)
	{
		TacoOrder theOrder = orderRepo.findById(orderId).get();
		if(patch.getDeliveryName() != null)
		{
			theOrder.setDeliveryName(patch.getDeliveryName());
		}
		if(patch.getDeliveryStreet() != null)
		{
			theOrder.setDeliveryStreet(patch.getDeliveryStreet());
		}
		if(patch.getDeliveryCity() != null)
		{
			theOrder.setDeliveryCity(patch.getDeliveryCity());
		}
		if(patch.getDeliveryState() != null)
		{
			theOrder.setDeliveryState(patch.getDeliveryState());
		}
		if (patch.getDeliveryZip() != null) {
			theOrder.setDeliveryZip(patch.getDeliveryZip());
		}
		if (patch.getCcNumber() != null) {
			theOrder.setCcNumber(patch.getCcNumber());
		}
		if (patch.getCcExpiration() != null) {
			theOrder.setCcExpiration(patch.getCcExpiration());
		}
		if (patch.getCcCVV() != null) {
			theOrder.setCcCVV(patch.getCcCVV());
		}
		return orderRepo.save(theOrder);
	}
	
	@DeleteMapping("/{orderId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteOrder(@PathVariable("orderId") Long orderId)
	{
		try {
			orderRepo.deleteById(orderId);
		} catch (Exception e) {
		}
	}
	
}
