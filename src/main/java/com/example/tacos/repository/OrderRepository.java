package com.example.tacos.repository;

import com.example.tacos.domain.TacoOrder;

public interface OrderRepository {

	TacoOrder save(TacoOrder tacoOrder);
}
