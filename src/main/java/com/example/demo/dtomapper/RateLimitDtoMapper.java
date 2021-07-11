package com.example.demo.dtomapper;

import com.example.demo.dto.RateLimitDTO;
import com.example.demo.dtomapper.interfaces.DTOMapper;
import com.example.demo.model.entities.RateLimit;

public class RateLimitDtoMapper implements DTOMapper<RateLimitDTO, RateLimit>{

	@Override
	public RateLimitDTO mapToDTO(RateLimit entity) {
		String daysLeft = entity.daysLeftOfBeingRateLimited().toString();
		if (daysLeft == null) {
			daysLeft = "0";
		}
		var rateLimitDTO = new RateLimitDTO(daysLeft, entity.getId(), entity.getPoliticianNumber());
		return rateLimitDTO;
	}

}
