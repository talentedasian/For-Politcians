package com.example.demo.domain.domainModel;

import com.example.demo.baseClasses.FakeDomainService;
import com.example.demo.baseClasses.NumberTestFactory;
import com.example.demo.domain.entities.Rating;
import com.example.demo.domain.entities.UserRateLimitService;
import com.example.demo.domain.politicians.PoliticianNumber;
import com.example.demo.domain.politicians.PoliticianTypes.SenatorialPolitician.SenatorialBuilder;
import com.example.demo.domain.politicians.Politicians;
import com.example.demo.domain.politicians.Politicians.PoliticiansBuilder;
import com.example.demo.exceptions.UserRateLimitedOnPoliticianException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.example.demo.baseClasses.BuilderFactory.createPolRating;
import static com.example.demo.baseClasses.BuilderFactory.createRater;
import static com.example.demo.baseClasses.NumberTestFactory.POL_NUMBER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PoliticiansTest {

	final PoliticianNumber POLITICIAN_NUMBER = POL_NUMBER();
	
	PoliticiansBuilder politicianBuilder = new PoliticiansBuilder(POLITICIAN_NUMBER)
			.setFirstName("Test")
			.setLastName("Name")
			.setRating(new Rating(0D, 0D))
			.setPoliticiansRating(null);

	UserRateLimitService fakeDomainService = FakeDomainService.unliRateService();

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

		var raterThatsNotRateLimited = createRater("FLPOM-00003123");

		Politicians politician = politicianBuilder.build();
		var firstRating = createPolRating(2.243, rater, politician);
		var secondRating = createPolRating(3.2232, raterThatsNotRateLimited, politician);

		firstRating.ratePolitician(fakeDomainService);
		secondRating.ratePolitician(fakeDomainService);

		secondRating.deleteRating();

		assertThat(politician.countsOfRatings())
				.isEqualTo(1);
	}

	@Test
	public void totalCountsOfRatingsShouldStillBe2WhenRaterDeletesPolitician() throws UserRateLimitedOnPoliticianException {
		int EXPECTED_NUMBER_OF_RATINGS = 2;

		var rater = createRater(NumberTestFactory.ACC_NUMBER().accountNumber());

		var raterThatsNotRateLimited = createRater("FLPOM-00003123");

		Politicians politician = politicianBuilder.build();
		var firstRating = createPolRating(2.243, rater, politician);
		var secondRating = createPolRating(3.2232, raterThatsNotRateLimited, politician);

		firstRating.ratePolitician(fakeDomainService);
		secondRating.ratePolitician(fakeDomainService);

		secondRating.deleteRating();

		assertThat(politician.countsOfRatings())
				.isEqualTo(1);
		assertThat(politician.totalCountsOfRatings())
				.isEqualTo(EXPECTED_NUMBER_OF_RATINGS);
	}

}