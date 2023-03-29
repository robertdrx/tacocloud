package com.example.tacos.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.example.tacos.domain.TacoOrder;
import com.example.tacos.model.User;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {

	List<TacoOrder> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);

}
