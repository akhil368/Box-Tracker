package com.purpledocs.boxtracker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.purpledocs.boxtracker.entity.Box;

public interface BoxRepository extends JpaRepository<Box, Integer> {

	
	public Optional<Box> findByBoxScanId(String boxScanId);
}
