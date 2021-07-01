package com.example.demo.dtomapper;

import com.example.demo.dto.RateLimitDto;
import com.example.demo.dtomapper.interfaces.DTOMapper;
import com.example.demo.model.entities.RateLimit;

public class RateLimitDtoMapper implements DTOMapper<RateLimitDto, RateLimit>{

	@Override
	public RateLimitDto mapToDTO(RateLimit entity) {
		String daysLeft = entity.daysLeftOfBeingRateLimited().toString();
		if (daysLeft == null) {
			daysLeft = "0";
		}
		var rateLimitDTO = new RateLimitDto(daysLeft, entity.getId(), entity.getPoliticianNumber());
		return rateLimitDTO;
	}

}
