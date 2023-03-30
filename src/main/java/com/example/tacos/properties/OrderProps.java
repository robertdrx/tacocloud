package com.example.tacos.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Component
@Data
@Validated
@ConfigurationProperties(prefix="taco.orders")
public class OrderProps {

	@Min(value=5, message="Min value must be 5")
	@Max(value=25, message="Max value must be 25")
	private int pageSize = 20;
}
