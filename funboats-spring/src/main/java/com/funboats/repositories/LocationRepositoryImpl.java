package com.funboats.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.funboats.model.Location;
import com.funboats.repositories.maps.LocationRowMapper;

@Repository
public class LocationRepositoryImpl implements LocationRepository{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * return total row count in locations
	 * return Long
	 */
	public Long getCount(){
    	String sql = "SELECT COUNT(*) FROM locations";
    	return jdbcTemplate.queryForObject(sql, Long.class);
    }
	
	/**
	 * return list of locations
	 * @return List<Location>
	 */
	public List<Location> findAll() {		
		String sql = "SELECT * FROM locations";
		return jdbcTemplate.query(sql, new LocationRowMapper());
	}
}
