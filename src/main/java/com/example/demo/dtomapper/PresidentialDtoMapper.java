package com.example.demo.dtomapper;

import static java.util.stream.Collectors.toList;

import java.util.List;

import com.example.demo.dto.PresidentialPoliticianDTO;
import com.example.demo.dtomapper.interfaces.PoliticianDTOMapper;
import com.example.demo.model.entities.Politicians;
import com.example.demo.model.entities.politicians.PoliticianTypes.PresidentialPolitician;
import com.example.demo.model.enums.Rating;

public class PresidentialDtoMapper extends PoliticiansDtoMapper implements PoliticianDTOMapper {

	@Override
	public PresidentialPoliticianDTO mapToDTO(Politicians entity) {
		org.springframework.util.Assert.state(PresidentialPolitician.class.isInstance(entity),
				"entity must be of type presidential");
		return mapToDto((PresidentialPolitician) entity);
	}
	
	@Override
	public List<PresidentialPoliticianDTO> mapToDTO(List<Politicians> entity) {
		if (entity.size() == 0) {
			return List.of();
		}
		
		org.springframework.util.Assert.state(PresidentialPolitician.class.isInstance(entity.get(0)),
				"entity must be of type presidential");
		return entity.stream()
				.map(politicians -> mapToDto((PresidentialPolitician) politicians))
				.collect(toList());
	}



	private PresidentialPoliticianDTO mapToDto(PresidentialPolitician entity) {
		Double rating = entity.getRating().getAverageRating();
		Rating satisfactionRate = Rating.mapToSatisfactionRate(rating);
		
		return new PresidentialPoliticianDTO(entity, satisfactionRate, entity.getMostSignificantLawSigned());
	}

}
