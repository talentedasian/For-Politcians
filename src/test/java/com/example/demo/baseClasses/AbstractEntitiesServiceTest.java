package com.example.demo.baseClasses;

import javax.servlet.http.HttpServletRequest;

import org.mockito.Mock;

import com.example.demo.model.averageCalculator.AverageCalculator;
import com.example.demo.model.entities.Politicians;
import com.example.demo.model.entities.PoliticiansRating;
import com.example.demo.repository.PoliticiansRepository;
import com.example.demo.repository.RatingRepository;
import com.example.demo.service.PoliticiansService;
import com.example.demo.service.RatingService;

public class AbstractEntitiesServiceTest {

	@Mock
	public RatingRepository ratingRepo;
	@Mock
	public PoliticiansRepository politicianRepo;
	@Mock
	public HttpServletRequest req;
	@Mock
	public AverageCalculator calculator;
	
	public PoliticiansService politicianService;
	
	public RatingService ratingService;
	public PoliticiansRating rating;
	public Politicians politician;
	public final String polNumber = "FL00-LF00-0000";
	
	
}
