package com.purpledocs.boxtracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.purpledocs.boxtracker.entity.Location;

public interface LocationRepository extends JpaRepository<Location, Integer> {

}
