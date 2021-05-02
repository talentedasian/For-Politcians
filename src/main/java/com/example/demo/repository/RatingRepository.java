package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.PoliticiansRating;

public interface RatingRepository extends JpaRepository<PoliticiansRating, Integer>{
	
	Optional<PoliticiansRating> findByRater_FacebookName(String facebookName);

}
