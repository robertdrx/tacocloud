package com.example.tacos.repository;

import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.tacos.domain.Ingredient;
import com.example.tacos.domain.Taco;
import com.example.tacos.domain.TacoOrder;

@Repository
public class JdbcOrderRepository implements OrderRepository {

	private JdbcOperations jdbcOperations;
	
	public JdbcOrderRepository(JdbcOperations jdbcOperations)
	{
		this.jdbcOperations = jdbcOperations;
	}
	
	@Override
	@Transactional
	public TacoOrder save(TacoOrder order) {
		PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
				"insert into Taco_Order "
				+ "(delivery_name, delivery_street, delivery_city, "
				+ "delivery_state, delivery_zip, cc_number, "
				+ "cc_expiration, cc_cvv, placed_at) "
				+ "values (?,?,?,?,?,?,?,?,?)",
				Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,
				Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.TIMESTAMP
				);
		
		pscf.setReturnGeneratedKeys(true);
		
		order.setPlacedAt(new Date());
		
		PreparedStatementCreator psc = pscf.newPreparedStatementCreator(
				Arrays.asList(
						order.getDeliveryName(),
						order.getDeliveryStreet(),
						order.getDeliveryCity(),
						order.getDeliveryState(),
						order.getDeliveryZip(),
						order.getCcNumber(),
						order.getCcExpiration(),
						order.getCcCVV(),
						order.getPlacedAt()
						)
				);
		
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcOperations.update(psc, keyHolder);
		long orderId = keyHolder.getKey().longValue();
		order.setId(orderId);
		
		List<Taco> tacos = order.getTacos();
		int i=0;
		for(Taco taco : tacos)
		{
			saveTaco(orderId, i++, taco);
		}
		
		return order;
	}

	private long saveTaco(long orderId, int orderKey, Taco taco) {
		taco.setCreatedAt(new Date());
		PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
				"insert into Taco "
				+ "(name, created_at, taco_order, taco_order_key) "
				+ "values (?,?,?,?)",
				Types.VARCHAR, Types.TIMESTAMP, Types.BIGINT, Types.BIGINT
				);
		
		pscf.setReturnGeneratedKeys(true);
		
		PreparedStatementCreator psc = pscf.newPreparedStatementCreator(Arrays.asList(
				taco.getName(),
				taco.getCreatedAt(),
				orderId,
				orderKey
				));
		
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcOperations.update(psc, keyHolder);
		long tacoId = keyHolder.getKey().longValue();
		
		saveIngredientRefs(tacoId, taco.getIngredients());
		
		return tacoId;
	}

	private void saveIngredientRefs(long tacoId, List<Ingredient> ingredients) {
		int key = 0;
		for(Ingredient ingredientRef : ingredients)
		{
			jdbcOperations.update(
					"insert into Ingredient_Ref (ingredient, taco, taco_key) "
					+ "values (?,?,?)",
					ingredientRef.getId(),
					tacoId,
					key++
					);
		}
		
	}
	
	

}