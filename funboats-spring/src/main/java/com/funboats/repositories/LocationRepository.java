package com.funboats.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.funboats.model.Location;

@Repository
public interface LocationRepository {

	List<Location> findAll();
	Long getCount();
}
