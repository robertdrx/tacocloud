package com.example.tacos.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.tacos.domain.Ingredient;

@Repository
public class JdbcIngredientRepository implements IngredientRepository {

	private JdbcTemplate jdbcTemplate;
	
	public JdbcIngredientRepository(JdbcTemplate jdbcTemplate)
	{
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public Iterable<Ingredient> findAll() {
		// TODO Auto-generated method stub
		return jdbcTemplate.query("select id, name, type from Ingredient", this::mapRowToIngredient);
	}
	
	private Ingredient mapRowToIngredient(ResultSet row, int rowNum) throws SQLException
	{
		return new Ingredient(
				row.getString("id"),
				row.getString("name"),
				Ingredient.Type.valueOf(row.getString("type"))
				);
	}

	@Override
	public Optional<Ingredient> findById(String id) {
		// TODO Auto-generated method stub
		List<Ingredient> results = jdbcTemplate.query("select id, name, type from Ingredient where id=?", this::mapRowToIngredient, id);
		return results.size() == 0 ? Optional.empty() : Optional.of(results.get(0));
	}

	@Override
	public Ingredient save(Ingredient ingredient) {
		// TODO Auto-generated method stub
		jdbcTemplate.update(
				"insert into Ingredient (id, name, type) values (?,?,?)",
				ingredient.getId(),
				ingredient.getName(),
				ingredient.getType().toString()
				);
		return ingredient;
	}

}
