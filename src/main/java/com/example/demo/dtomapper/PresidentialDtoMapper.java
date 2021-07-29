package com.example.demo.dtomapper;

import com.example.demo.dto.PresidentialPoliticianDTO;
import com.example.demo.model.entities.Politicians;
import com.example.demo.model.entities.politicians.PoliticianTypes.PresidentialPolitician;
import com.example.demo.model.enums.Rating;

public class PresidentialDtoMapper extends PoliticiansDtoMapper {

	@Override
	public PresidentialPoliticianDTO mapToDTO(Politicians entity) {
		org.springframework.util.Assert.state(PresidentialPolitician.class.isInstance(entity),
				"entity must be of type presidetial");
		return mapToDto((PresidentialPolitician) entity);
	}

	private PresidentialPoliticianDTO mapToDto(PresidentialPolitician entity) {
		Double rating = entity.getRating().getAverageRating();
		Rating satisfactionRate = Rating.mapToSatisfactionRate(rating);
		
		return new PresidentialPoliticianDTO(entity, satisfactionRate, entity.getMostSignificantLawSigned());
	}

}
