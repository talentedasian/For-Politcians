package com.example.demo.domain.entities;

import com.example.demo.baseClasses.NumberTestFactory;
import com.example.demo.domain.AverageRating;
import com.example.demo.domain.Score;
import com.example.demo.domain.entities.PoliticianTypes.SenatorialPolitician.SenatorialBuilder;
import com.example.demo.domain.entities.Politicians.PoliticiansBuilder;
import com.example.demo.exceptions.UserRateLimitedOnPoliticianException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.example.demo.baseClasses.BuilderFactory.createPolRating;
import static com.example.demo.baseClasses.BuilderFactory.createRater;
import static com.example.demo.baseClasses.NumberTestFactory.ACC_NUMBER;
import static com.example.demo.baseClasses.NumberTestFactory.POL_NUMBER;
import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PoliticiansTest {

	final PoliticianNumber POLITICIAN_NUMBER = POL_NUMBER();
	
	PoliticiansBuilder politicianBuilder = new PoliticiansBuilder(POLITICIAN_NUMBER)
			.setFirstName("Test")
			.setLastName("Name")
			.setRating(new Rating(0D, AverageRating.of(valueOf(1d))));

	@Test
	public void testNullLastNameInBuilder() {
		var politicianWithNoLastName = new PoliticiansBuilder(POLITICIAN_NUMBER)
				.setFirstName("Test")
				.setFullName()
				.build();

		assertEquals("Test", politicianWithNoLastName.fullName());
	}

	@Test
	public void testFullNameInBuilderWithNoFirstName() throws Exception{
		var politicianWithNullFirstName = new PoliticiansBuilder(POLITICIAN_NUMBER)
				.setLastName("Name")
				.build();

		assertThrows(IllegalStateException.class, () -> politicianWithNullFirstName.fullName());
	}

	@Test
	public void testFullNameInBuilder() {
		var politicianWithFirstAndLastName = politicianBuilder
				.setFullName()
				.build();

		assertEquals("Test Name", politicianWithFirstAndLastName.fullName());
	}

	@Test
	public void testHashCodeActuallyWorksAsIntended() {
		int expectedHashCode = new SenatorialBuilder(politicianBuilder)
				.setTotalMonthsOfService(12)
				.build()
				.hashCode();
		Politicians politician = politicianBuilder.build();
		var senatorial = politician.setType(Politicians.Type.SENATORIAL);
		int actualHashCode = politician.hashCode();

		Assertions.assertEquals(expectedHashCode, actualHashCode);
	}

	@Test
	public void countsOfRatingsShouldDecreaseWhenADeleteOfRatingHappens() throws UserRateLimitedOnPoliticianException {
		var rater = createRater(NumberTestFactory.ACC_NUMBER().accountNumber());

		Politicians politician = politicianBuilder.build();
		var rating = createPolRating(Score.of(2.243), rater, politician);

		politician.rate(rating);
		politician.rate(rating);

		politician.deleteRate(rating);

		assertThat(politician.countsOfRatings())
				.isEqualTo(1);
	}

	@Test
	public void totalCountsOfRatingsShouldStillBe2WhenRaterDeletesPolitician() throws UserRateLimitedOnPoliticianException {
		int EXPECTED_NUMBER_OF_RATINGS = 2;

		var rater = createRater(NumberTestFactory.ACC_NUMBER().accountNumber());

		Politicians politician = politicianBuilder.build();
		var rating = createPolRating(Score.of(2.243), rater, politician);

		politician.rate(rating);
		politician.rate(rating);

		politician.deleteRate(rating);

		assertThat(politician.totalCountsOfRatings())
				.isEqualTo(EXPECTED_NUMBER_OF_RATINGS);
	}

	@Test
	public void testCalculateAverageRatingMethod() throws Exception{
		double EXPECTED_AVERAGE_RATING = 2;

		var rater = createRater(ACC_NUMBER().accountNumber());

		var justHereToPutIncreaseSize = createPolRating(Score.of(1), rater, politicianBuilder.build());

		Politicians politician = politicianBuilder
				.setRating(new Rating(3D, AverageRating.of(valueOf(2.231))))
				.setPoliticiansRating(List.of(justHereToPutIncreaseSize, justHereToPutIncreaseSize)).build();

		politician.calculateAverageRating(Score.of(1d));

		assertThat(politician.averageRating())
				.isEqualTo(EXPECTED_AVERAGE_RATING);
	}

	@Test
	public void testAverageRating() throws Exception{
		double EXPECTED_AVERAGE_RATING = 1.556;

		var rater = createRater(NumberTestFactory.ACC_NUMBER().accountNumber());

		var justHereToPutIncreaseSize = createPolRating(Score.of(1), rater, politicianBuilder.build());

		Politicians politician = politicianBuilder
				.setRating(new Rating(3D, AverageRating.of(valueOf(2.231))))
				.setPoliticiansRating(List.of(justHereToPutIncreaseSize, justHereToPutIncreaseSize, justHereToPutIncreaseSize)).build();

		var actualRating = createPolRating(Score.of(3.2232), rater, politician);

		politician.rate(actualRating);

		assertThat(politician.averageRating())
				.isEqualTo(EXPECTED_AVERAGE_RATING);
	}

}