package com.example.demo.logger;

import org.aspectj.lang.annotation.Aspect;

@Aspect
public class PoliticiansLogger {

//	@AfterReturning(
//			pointcut = "execution(* com.example.demo.adapter.in.service.PoliticiansService.savePolitician(..))",
//			returning = "politician"
//			)
//	public void logSuccessfulSavedPolitician(Politicians politician) {
//		MDC.put("fullname", politician.fullName());
//		MDC.put("polNumber", politician.retrievePoliticianNumber());
//
//		LoggerFactory.getLogger(PoliticiansLogger.class).info("saved with");
//
//		MDC.clear();
//	}

}
