package com.purpledocs.boxtracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.purpledocs.boxtracker.entity.BoxDetails;

public interface BoxDetailsRepository extends JpaRepository<BoxDetails, Integer> {

}
