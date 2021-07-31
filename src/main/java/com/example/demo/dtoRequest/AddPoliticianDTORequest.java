package com.example.demo.dtoRequest;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
		@JsonSubTypes.Type(value = AddSenatorialPoliticianDTORequest.class, name = "Senatorial"),
		@JsonSubTypes.Type(value = AddSenatorialPoliticianDTORequest.class, name = "Presidential")
})
public class AddPoliticianDTORequest {

	@NotNull
	private String firstName;
	
	@NotNull
	private String lastName;
	
	@NotNull
	@DecimalMin("0.01")
	@DecimalMax("9.99")
	private BigDecimal rating;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public BigDecimal getRating() {
		return rating;
	}

	public void setRating(BigDecimal rating) {
		this.rating = rating;
	}

	public AddPoliticianDTORequest() {
		super();
	}

	public AddPoliticianDTORequest(@NotNull String firstName, @NotNull String lastName,
			@NotNull @DecimalMin("0.01") @DecimalMax("9.99") BigDecimal rating) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.rating = rating;
	}

}
