package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.entities.PoliticiansRating;

public interface RatingRepository extends JpaRepository<PoliticiansRating, Integer>{
	
	List<PoliticiansRating> findByRater_Email(String email);

	long countByPolitician_Id(Integer id);

}
