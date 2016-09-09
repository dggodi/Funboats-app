package com.funboats.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.funboats.model.Location;
import com.funboats.repositories.LocationRepository;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

	@Autowired
	private LocationRepository locationRepository;
	
	/**
	 * return a list of locations stored in DB
	 * @return List<Location>
	 */
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public List<Location> findAll(){
		System.out.println("LocationController :: findall");
		return locationRepository.findAll();
	}
	
}
