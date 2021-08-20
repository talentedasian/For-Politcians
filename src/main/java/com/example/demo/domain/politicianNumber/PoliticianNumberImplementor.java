package com.example.demo.domain.politicianNumber;

import com.example.demo.domain.politicians.Name;
import com.example.demo.domain.politicians.PoliticianNumber;
import com.example.demo.domain.politicians.Politicians;

public class PoliticianNumberImplementor extends AbstractPoliticianNumber{

	final String pattern = PoliticianNumber.pattern;

	final char FIRSTNAME_INITIAL = 'F';
	final char LASTNAME_INITIAL = 'L';
	final char TYPE_INITIAL = 'T';

	private static Politicians politician;

	Politicians getPolitician() {
		return this.politician;
	}

	PoliticianNumberImplementor(Name name, String polNumber) {
		super(name, polNumber);
	}

	public static PoliticianNumberImplementor with(Name name, Politicians.Type type) {
		var pol = new Politicians.PoliticiansBuilder()
				.setFirstName(name.firstName())
				.setLastName(name.lastName())
				.build();
		pol.setType(type);
		politician = pol;
		return new PoliticianNumberImplementor(name, "dummy");
	}

	@Override
	PoliticianNumberImplementor calculatePoliticianNumber() {
		org.springframework.util.Assert.state(politician.getType() != null,
				"politician  must be a subclass of class \"Politicians\"");
		var name = new Name(firstName, lastName);

		switch (politician.getType()) {
			case PRESIDENTIAL -> {return PresidentialNumberImplementor.with(name).calculatePoliticianNumber();}
			case SENATORIAL -> {return SenatorialNumberImplementor.with(name).calculatePoliticianNumber();}
			default -> throw new IllegalStateException("type is non existent");
		}
	}

}
