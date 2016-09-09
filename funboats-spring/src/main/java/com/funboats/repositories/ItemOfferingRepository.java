package com.funboats.repositories;

import java.util.List;

import com.funboats.model.ItemOffering;
import com.funboats.model.SearchObject;

public interface ItemOfferingRepository {

	List<ItemOffering> findAll();

	ItemOffering searchById(SearchObject obj);

	boolean save(ItemOffering itemOffering);

	boolean update(ItemOffering itemOffering);

	boolean delete(SearchObject obj);
	
	List<ItemOffering> searchByLocation(SearchObject obj);

	List<ItemOffering> searchByLike(SearchObject obj);
	
	List<ItemOffering> searchByFilter(ItemOffering itemOffering);

}
