package com.example.demo.dtomapper;

import com.example.demo.adapter.web.dto.PoliticianDto;
import com.example.demo.adapter.web.dto.PresidentialPoliticianDto;
import com.example.demo.domain.entities.PoliticianTypes.PresidentialPolitician;
import com.example.demo.domain.entities.Politicians;
import com.example.demo.domain.enums.Rating;

import java.util.List;

import static java.util.stream.Collectors.toList;

/*
	Callers must use only the superclass PoliticiansDtoMapper.
 */
class PresidentialDtoMapper extends PoliticiansDtoMapper {

	@Override
	public PresidentialPoliticianDto mapToDTO(Politicians entity) {
		org.springframework.util.Assert.state(entity instanceof PresidentialPolitician,
				"entity must be of type presidential");
		return mapToDto((PresidentialPolitician) entity);
	}
	
	@Override
	public List<PoliticianDto> mapToDTO(List<Politicians> entity) {
		if (entity.size() == 0) {
			return List.of();
		}

		return entity.stream()
				.map(politicians -> mapToDTO(politicians))
				.collect(toList());
	}

	private PresidentialPoliticianDto mapToDto(PresidentialPolitician entity) {
		double rating = entity.hasRating() ? Double.parseDouble(entity.averageRating()) : 0;
		Rating satisfactionRate = Rating.mapToSatisfactionRate(rating);
		
		return PresidentialPoliticianDto.of(entity, satisfactionRate, entity.getMostSignificantLawSigned());
	}

}
