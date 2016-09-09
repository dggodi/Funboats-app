package com.funboats.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.funboats.model.ItemOffering;
import com.funboats.model.SearchObject;
import com.funboats.repositories.ItemOfferingRepository;

/**
 * class maps http request
 */
@RestController
@RequestMapping("/api/jetskis")
public class ItemOfferingController{
	
	@Autowired
	private ItemOfferingRepository itemRepository;

	/**
	 * returns a list of a successful search
	 * @param obj represents the search object
	 * @return List<ItemOffering>
	 */
	@RequestMapping(value="/location/search", method=RequestMethod.POST)
	public List<ItemOffering> search(@RequestBody SearchObject obj){
		return itemRepository.searchByLocation(obj);
	}
	
	/**
	 * returns a list of a successful like search
	 * @param obj represents the search object
	 * @return List<ItemOffering>
	 */
	@RequestMapping(value="/search/like", method=RequestMethod.POST)
	public List<ItemOffering> searchByLike(@RequestBody SearchObject obj){		
		return itemRepository.searchByLike(obj);
	}
	
	/**
	 * returns a list of a successful search by more than one object
	 * @param obj represents the search object
	 * @return List<ItemOffering>
	 */
	@RequestMapping(value="/search/filter", method=RequestMethod.POST)
	public List<ItemOffering> searchByFilter(@RequestBody ItemOffering itemOffering){	
		return itemRepository.searchByFilter(itemOffering);
	}
	
	/**
	 * returns a list of all searched objects
	 * @param obj represents the search object
	 * @return List<ItemOffering>
	 */
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public List<ItemOffering> findAll(){
		return itemRepository.findAll();
	}
	
	/**
	 * returns a list of a successful search
	 * @param id represents the search object
	 * @return ItemOffering
	 */
	@RequestMapping(value="/list/id", method=RequestMethod.POST)
	public ItemOffering findById(@RequestBody SearchObject obj){		
		return itemRepository.searchById(obj);
	}
	
	/**
	 * return true if save was successful
	 * @param itemOffering object to add to DB
	 * @return booelan
	 */
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public boolean add(@RequestBody ItemOffering itemOffering){	
		System.out.println("ItemOfferingController:: add  ");
		return itemRepository.save(itemOffering);
	}
	
	/**
	 * return true if update was successful
	 * @param itemOffering object to update in DB
	 * @return booelan
	 */
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public boolean update(@RequestBody ItemOffering itemOffering){	
		return itemRepository.update(itemOffering);
	}
	
	/**
	 * return true if obj was deleted
	 * @param id identifies the object to be deleted
	 * @return boolean
	 */
	@RequestMapping(value="/delete/", method=RequestMethod.POST)
	public boolean delete(@RequestBody SearchObject obj){	
		return itemRepository.delete(obj);
	}
	
}
