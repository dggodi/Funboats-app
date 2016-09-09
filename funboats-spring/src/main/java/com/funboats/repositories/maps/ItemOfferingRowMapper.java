package com.funboats.repositories.maps;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.jdbc.core.RowMapper;

import com.funboats.model.Item;
import com.funboats.model.ItemImage;
import com.funboats.model.ItemOffering;
import com.funboats.model.Location;

/**
 * class maps the rows in itemOffering and items to pojo classes ItemOffering and Item
 */
public class ItemOfferingRowMapper implements RowMapper<ItemOffering> {

	@Override
	public ItemOffering mapRow(ResultSet rs, int row) throws SQLException {
		
		Item item = new Item();
		item.setItem_id(rs.getLong("item_id"));
		item.setHours(rs.getInt("hours"));
		item.setDisplacement(rs.getInt("displacement"));
		item.setColor(rs.getString("color"));
		item.setCategory(rs.getString("category"));
		item.setSeating(rs.getString("seating"));
		item.setVinNo(rs.getString("vinNo"));
		item.setEngine(rs.getString("engine"));
		item.setPumpType(rs.getString("pumpType"));
		item.setFuelcapacity(rs.getInt("fuelCap"));

		ItemOffering itemOffering = new ItemOffering();
		itemOffering.setItemOfferingId(rs.getLong("item_offering_id"));
		itemOffering.setBrand(rs.getString("brand"));
		itemOffering.setModel(rs.getString("model"));
		itemOffering.setYear(rs.getInt("year"));
		itemOffering.setCost(rs.getInt("cost"));
		itemOffering.setDescript(rs.getString("descript"));
		itemOffering.setLocation(rs.getString("location"));
		itemOffering.setUsername(rs.getString("user_name"));
		itemOffering.setItem(item);
		
		return itemOffering;
	}
}
