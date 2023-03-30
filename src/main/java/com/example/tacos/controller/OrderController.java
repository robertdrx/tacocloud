package com.example.tacos.controller;

import java.util.Date;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.example.tacos.domain.TacoOrder;
import com.example.tacos.model.User;
import com.example.tacos.properties.OrderProps;
import com.example.tacos.repository.OrderRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "taco.orders")
public class OrderController {
	
	private final OrderProps orderProps;
	private final OrderRepository orderRepo;
	
	@GetMapping("/current")
	public String orderForm()
	{
		return "orderForm";
	}
	
	@PostMapping
	public String processOrder(@Valid TacoOrder order, Errors errors, 
			SessionStatus sessionStatus,
			@AuthenticationPrincipal User user
			)
	{
		if(errors.hasErrors())
		{
			return "orderForm";
		}
		
		order.setUser(user);
		order.setPlacedAt(new Date());
		TacoOrder orderSaved = orderRepo.save(order);
		
		log.info("Order submitted: {}", orderSaved);
		sessionStatus.setComplete();
		return "redirect:/";
	}
	
	@GetMapping
	public String orderForUser(@AuthenticationPrincipal User user, Model model)
	{
		Pageable pageable = PageRequest.of(0, orderProps.getPageSize());
		model.addAttribute("orders", orderRepo.findByUserOrderByPlacedAtDesc(user, pageable));
		model.addAttribute("user", user);
		return "orderList";
	}
}
