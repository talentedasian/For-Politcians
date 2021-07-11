package com.example.demo.dtomapper;

import com.example.demo.dto.RateLimitDTO;
import com.example.demo.dtomapper.interfaces.DTOMapper;
import com.example.demo.model.entities.RateLimit;

public class RateLimitDtoMapper implements DTOMapper<RateLimitDTO, RateLimit>{

	@Override
	public RateLimitDTO mapToDTO(RateLimit entity) {
		Integer daysLeft = entity.daysLeftOfBeingRateLimited();
		String days = null;
		
		if (daysLeft == null) {
			days = "0";
		} else {
			days = daysLeft.toString();
		}
		
		var rateLimitDTO = new RateLimitDTO(days, entity.getId(), entity.getPoliticianNumber());
		return rateLimitDTO;
	}

}
