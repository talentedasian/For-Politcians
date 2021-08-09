package com.example.demo.logger;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.jboss.logging.MDC;
import org.slf4j.LoggerFactory;

import com.example.demo.domain.politicians.Politicians;

@Aspect
public class PoliticiansLogger {

	@AfterReturning(
			pointcut = "execution(* com.example.demo.adapter.in.service.PoliticiansService.savePolitician(..))",
			returning = "politician"
			)
	public void logSuccessfulSavedPolitician(Politicians politician) {
		MDC.put("fullname", politician.getFullName());
		MDC.put("polNumber", politician.getPoliticianNumber());
		
		LoggerFactory.getLogger(PoliticiansLogger.class).info("saved with");
		
		MDC.clear();
	}

}
