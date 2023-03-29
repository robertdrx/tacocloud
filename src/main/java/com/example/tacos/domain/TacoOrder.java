package com.example.tacos.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.CreditCardNumber;

import com.example.tacos.model.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
public class TacoOrder {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private Date placedAt;
	
	@ManyToOne
	private User user;	
	
	@NotBlank(message="Delivery name is required")
	private String deliveryName;
	
	@NotBlank(message="Street is required")
	private String deliveryStreet;
	
	@NotBlank(message="City is required")
	private String deliveryCity;
	
	@NotBlank(message="State is required")
	private String deliveryState;
	
	@NotBlank(message="Zip is required")
	private String deliveryZip;
	
	@CreditCardNumber(message="Not a valid credit card number")
	private String ccNumber;
	
	@Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([2-9][0-9])$")
	private String ccExpiration;
	
	@Digits(integer=3, fraction=0, message="Invalid CVV")
	private String ccCVV;
	
	@ManyToMany
	private List<Taco> tacos = new ArrayList<>();
	
	public void addTaco(Taco taco)
	{
		this.tacos.add(taco);
	}
}
