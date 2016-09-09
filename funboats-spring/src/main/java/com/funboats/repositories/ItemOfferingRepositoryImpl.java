package com.funboats.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.funboats.model.ItemOffering;
import com.funboats.model.SearchObject;
import com.funboats.repositories.maps.ItemOfferingRowMapper;

@Controller
public class ItemOfferingRepositoryImpl implements ItemOfferingRepository{

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 	return id of object is search for object in DB is successful
	 *  @return Long
	 */
	public Long getCount(){
    	String sql = "SELECT COUNT(*) FROM itemofferings";
    	return jdbcTemplate.queryForObject(sql, Long.class);
    }
	
	/**
	 * return list off all ItemOffering objects searched for
	 * @return List<ItemOffering>
	 */
	public List<ItemOffering> findAll() {	
		String sql = "SELECT * FROM items, itemofferings where "
				   + "item_offering_id = item_id";	  
		
		return jdbcTemplate.query(sql, new ItemOfferingRowMapper());
	}

	/**
	 * return ItemOffering if the object is found by id
	 * @return ItemOffering
	 */
	public ItemOffering searchById(SearchObject obj){		
		String sql = "SELECT * FROM items, itemofferings where item_offering_id = ? and "
				   + "item_offering_id = items.item_id";
		Object[] params = {obj.getId()};
		return this.jdbcTemplate.queryForObject(sql, params, new ItemOfferingRowMapper() );
	}
	
	/**
	 * return list off all ItemOffering objects searched by location
	 * @return List<ItemOffering>
	 */
	public List<ItemOffering> searchByLocation(SearchObject obj) {
		
		String sql = "SELECT * FROM items, itemofferings where item_offering_id = items.item_id and "
				   + "location = ?";
		
		Object[] params = {obj.getSearch()};
		
		return jdbcTemplate.query(sql, params, new ItemOfferingRowMapper());
	}

	/**
	 * return list off all ItemOffering objects searched by brand, year, cost, category
	 * @return List<ItemOffering>
	 */
	public List<ItemOffering> searchByFilter(ItemOffering itemOffering){		
		String sql = "SELECT * FROM items, itemofferings where item_offering_id = items.item_id AND "
				   + "brand = ? AND year = ? AND cost = ? AND category = ?";
		
		Object[] params = {
			itemOffering.getBrand(),
			itemOffering.getYear(),
			itemOffering.getCost(),
			itemOffering.getItem().getCategory()
		};

		return jdbcTemplate.query(sql, params, new ItemOfferingRowMapper());
	}
	
	/**
	 * return list off all ItemOffering objects searched for using LIKE
	 * @return List<ItemOffering>
	 */
	public List<ItemOffering> searchByLike(SearchObject obj){
	
		String sql = "SELECT * FROM items, itemofferings where item_offering_id = items.item_id AND "
				   + "brand LIKE ? OR model LIKE ? OR location LIKE ? OR color LIKE ? OR category LIKE ?";
		Object[] params = {
			"%" + obj.getSearch() + "%", 
			"%" + obj.getSearch() + "%", 
			"%" + obj.getSearch() + "%", 
			"%" + obj.getSearch() + "%", 
			"%" + obj.getSearch() + "%"
		};
		
		return jdbcTemplate.query(sql, params, new ItemOfferingRowMapper());
	}
	
	/**
	 * return true if object is successfully deleted by id
	 * @return boolean
	 */
	@Transactional
	public boolean delete(SearchObject obj){
		String sql = "DELETE FROM itemofferings WHERE item_offering_id = ?";
		jdbcTemplate.update(sql, obj.getId());
		
		sql = "DELETE FROM items WHERE item_id = ?";
		
		return (jdbcTemplate.update(sql, obj.getId())) > 0;
	}
	
	/**
	 * return true if save was successful
	 * @return boolean
	 * 
	 * note:  construct save query and calls saveAndFlush to save to DB
	 */
	public boolean save(ItemOffering itemOffering){		
		String sqlItems = "INSERT INTO items (hours, displacement, color, category, seating,";
		sqlItems       +=" vinNo, engine, pumpType, fuelcap) VALUES (?,?,?,?,?,?,?,?,?)";
   
		String sqlItemofferings = "INSERT INTO itemofferings (brand, model, year, cost, descript, location, user_name) ";
		sqlItemofferings += " VALUES (?,?,?,?,?,?,?)";	
		
		return saveAndFlush(itemOffering, sqlItemofferings, sqlItems, null);
	}
	
	/**
	 * return true if update was successful
	 * @return boolean
	 * 
	 * note:  construct update query and calls saveAndFlush to save to DB
	 */
	public boolean update(ItemOffering itemOffering){	
		String sqlItems = "UPDATE items SET hours=?, displacement=?, color=?, category=?, seating=?, ";
		       sqlItems+= "vinNo=?, engine=?, pumpType=?, fuelCap=? WHERE item_id=?";
		
		String sqlItemofferings = "UPDATE itemofferings SET brand=?, model=?, year=?, cost=?, ";
			   sqlItemofferings+= "descript=?, location=?, user_name=? WHERE item_offering_id=?";	
		
		Object id = itemOffering.getItemOfferingId();
		
		return saveAndFlush(itemOffering, sqlItemofferings, sqlItems, id);
	}
	
	/**
	 * return true if save was successful
	 * @param itemOffering		object to save to DB
	 * @param sqlItemofferings	query for ItemOfferings
	 * @param sqlItems			query for Items
	 * @param id				id for update or null for insert operation
	 * @return true
	 */
	@Transactional
	public boolean saveAndFlush(ItemOffering itemOffering, String sqlItemofferings, String sqlItems, Object id){
	
		List<Object> list = new ArrayList<>();
		list.add(itemOffering.getItem().getHours());
		list.add(itemOffering.getItem().getDisplacement());
		list.add(itemOffering.getItem().getColor());
		list.add(itemOffering.getItem().getCategory());
		list.add(itemOffering.getItem().getSeating());
		list.add(itemOffering.getItem().getVinNo());
		list.add(itemOffering.getItem().getEngine());
		list.add(itemOffering.getItem().getPumpType());
		list.add(itemOffering.getItem().getFuelcapacity());
		
		if(id != null)
			list.add(id);

		Object[] params = list.toArray(new Object[list.size()]);
		
		jdbcTemplate.update(sqlItems, params);
			
		list = new ArrayList<>();
		list.add(itemOffering.getBrand());
		list.add(itemOffering.getModel());
		list.add(itemOffering.getYear());
		list.add(itemOffering.getCost());
		list.add(itemOffering.getDescript());
		list.add(itemOffering.getLocation());
		list.add(itemOffering.getUsername());
				
		if(id != null)
			list.add(id);
		
		
		params = list.toArray(new Object[list.size()]);
				
		return (jdbcTemplate.update(sqlItemofferings, params)) > 0;
	}

	
}
