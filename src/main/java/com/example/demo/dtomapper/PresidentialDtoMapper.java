package com.example.demo.dtomapper;

import com.example.demo.dto.PoliticianDTO;
import com.example.demo.dto.PresidentialPoliticianDTO;
import com.example.demo.model.entities.Politicians;
import com.example.demo.model.entities.politicians.PoliticianTypes.PresidentialPolitician;
import com.example.demo.model.enums.Rating;
import org.springframework.util.Assert;

import java.util.List;

import static java.util.stream.Collectors.toList;

public final class PresidentialDtoMapper extends PoliticiansDtoMapper {

	@Override
	public PresidentialPoliticianDTO mapToDTO(Politicians entity) {
		org.springframework.util.Assert.state(PresidentialPolitician.class.isInstance(entity),
				"entity must be of type presidential");
		return mapToDto((PresidentialPolitician) entity);
	}
	
	@Override
	public List<PoliticianDTO> mapToDTO(List<Politicians> entity) {
		if (entity.size() == 0) {
			return List.of();
		}

		return entity.stream()
				.peek(politicians -> {
						Assert.state(PresidentialPolitician.class.isInstance(politicians), "entity must be presidential");
				})
				.map(politicians -> mapToDto((PresidentialPolitician) politicians))
				.collect(toList());
	}

	private PresidentialPoliticianDTO mapToDto(PresidentialPolitician entity) {
		Double rating = entity.getRating().getAverageRating();
		Rating satisfactionRate = Rating.mapToSatisfactionRate(rating);
		
		return new PresidentialPoliticianDTO(entity, satisfactionRate, entity.getMostSignificantLawSigned());
	}

}
