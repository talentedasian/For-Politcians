package com.example.demo.domain.politicianNumber;

import com.example.demo.domain.entities.Politicians;

public class PoliticianNumberCalculatorFactory {

	public static PoliticianNumberCalculator politicianCalculator(Politicians.Type type) {
		switch (type) {
			case PRESIDENTIAL -> {return PresidentialNumberImplementor.create();}
			case SENATORIAL -> {return SenatorialNumberCalculator.create();}
			default -> throw new IllegalStateException("type is non existent");
		}

	}

}
