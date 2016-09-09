package com.funboats.repositories.maps;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.funboats.model.Location;

/**
 * class maps the rows in locations to pojo class Location
 */
public class LocationRowMapper implements RowMapper<Location> {

	@Override
	public Location mapRow(ResultSet rs, int row) throws SQLException {
		Location loc = new Location();
		loc.setLocationId(rs.getLong("location_id"));
		loc.setName(rs.getString("name"));
		
		return loc;
	}

}
