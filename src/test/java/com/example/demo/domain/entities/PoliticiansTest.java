package com.example.demo.domain.entities;

import com.example.demo.baseClasses.NumberTestFactory;
import com.example.demo.domain.AverageRating;
import com.example.demo.domain.Score;
import com.example.demo.domain.TotalRatingAccumulated;
import com.example.demo.domain.entities.PoliticianTypes.SenatorialPolitician.SenatorialBuilder;
import com.example.demo.domain.entities.Politicians.PoliticiansBuilder;
import com.example.demo.exceptions.UserRateLimitedOnPoliticianException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static com.example.demo.baseClasses.BuilderFactory.createPolRating;
import static com.example.demo.baseClasses.BuilderFactory.createRater;
import static com.example.demo.baseClasses.NumberTestFactory.ACC_NUMBER;
import static com.example.demo.baseClasses.NumberTestFactory.POL_NUMBER;
import static com.example.demo.domain.AverageRating.NO_RATING_YET;
import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Tag("Domain")
public class PoliticiansTest {

	final PoliticianNumber POLITICIAN_NUMBER = POL_NUMBER();
	
	PoliticiansBuilder politicianBuilder;

	@BeforeEach
	public void setup() {
		politicianBuilder = new PoliticiansBuilder(POLITICIAN_NUMBER)
				.setFirstName("Test")
				.setLastName("Name");
	}

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
	public void politicianShouldHaveNoAverageRatingWhenJustCreatedAndNoAverageRatingIsSpecified() throws Exception{
		Politicians politician = politicianBuilder.build();

		AverageRating averageRating = politician.average();

		assertThat(averageRating)
				.isEqualTo(NO_RATING_YET);
	}

	@Test
	public void shouldReturnAverageWithValueOfTheScoreToAddAsAverageRatingWhenPoliticianHasNoAverageRatingYet() throws Exception{
		Politicians politician = politicianBuilder.build();

		Score score = Score.of(2.232);

		assertThat(politician.calculateAverageRating(score))
				.isEqualTo(AverageRating.of(valueOf(score.rating())));
	}

	@Test
	public void totalRatingAccumulatedDefaultsToZeroIfConstructingPoliticianWithNoTotalRatingAccumulated() throws Exception{
		Politicians politician = politicianBuilder.build();

		BigDecimal totalRatingAccumulated = politician.totalRatingAccumulated().totalRating();

		assertThat(totalRatingAccumulated)
				.isEqualTo(valueOf(0));
	}

	@Test
	public void totalRatingAccumulatedShouldBeWhatIsSpecifiedIfSpecifiedInCreation() throws Exception{
		TotalRatingAccumulated totalRatingAccumulated = TotalRatingAccumulated.of(BigDecimal.TEN);
		Politicians politician = politicianBuilder.setTotalRating(totalRatingAccumulated).build();

		assertThat(politician.totalRatingAccumulated().totalRating())
				.isEqualByComparingTo(totalRatingAccumulated.totalRating());
	}

	@Test
	public void totalRatingAccumulatedShouldBeWhatIsSpecifiedIfSpecifiedInCreationWithLowRatingScaleLargerThan3() throws Exception{
		double EXPECTED_TOTAL_RATING_CALCULATED_FOR_LOW_RATING = 2.324;

		TotalRatingAccumulated totalRatingAccumulated = TotalRatingAccumulated.of(valueOf(2.3234));
		Politicians politician = politicianBuilder.setTotalRating(totalRatingAccumulated)
				.setAverageRating(AverageRating.of(valueOf(2.344))).build();

		assertThat(politician.totalRatingAccumulated().totalRating().doubleValue())
				.isEqualTo(EXPECTED_TOTAL_RATING_CALCULATED_FOR_LOW_RATING);
	}

	@Test
	public void countsOfRatingsShouldDecreaseWhenADeleteOfRatingHappens() throws UserRateLimitedOnPoliticianException {
		var rater = createRater(NumberTestFactory.ACC_NUMBER().accountNumber());

		Politicians politician = politicianBuilder.setRating(new Rating(0D, AverageRating.of(valueOf(1d)))).build();
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

		Politicians politician = politicianBuilder
				.setTotalRating(TotalRatingAccumulated.ZERO)
				.setAverageRating(NO_RATING_YET).build();
		var rating = createPolRating(Score.of(2.243), rater, politician);

		politician.rate(rating);
		politician.rate(rating);

		politician.deleteRate(rating);

		assertThat(politician.totalCountsOfRatings())
				.isEqualTo(EXPECTED_NUMBER_OF_RATINGS);
	}

	@Test
	public void testCalculateAverageRatingMethodThatAlreadyHasARatingBeforeHand() throws Exception{
		double EXPECTED_AVERAGE_RATING = 2;

		var rater = createRater(ACC_NUMBER().accountNumber());

		var justHereToIncreaseSize = createPolRating(Score.of(1), rater, politicianBuilder.build());

		Politicians politician = politicianBuilder
				.setTotalRating(TotalRatingAccumulated.of(valueOf(3)))
				.setAverageRating(AverageRating.of(valueOf(2.231)))
				.setPoliticiansRating(List.of(justHereToIncreaseSize, justHereToIncreaseSize))
				.build();

		assertThat(politician.calculateAverageRating(Score.of(1)))
				.isEqualTo(AverageRating.of(valueOf(EXPECTED_AVERAGE_RATING)));
	}

	@Test
	public void testAverageRating() throws Exception{
		double EXPECTED_AVERAGE_RATING = 1.556;

		var rater = createRater(NumberTestFactory.ACC_NUMBER().accountNumber());

		var justHereToPutIncreaseSize = createPolRating(Score.of(1), rater, politicianBuilder.build());

		Politicians politician = politicianBuilder
				.setAverageRating(AverageRating.of(valueOf(2.231)))
				.setTotalRating(TotalRatingAccumulated.of(valueOf(3)))
				.setPoliticiansRating(List.of(justHereToPutIncreaseSize, justHereToPutIncreaseSize, justHereToPutIncreaseSize)).build();

		var actualRating = createPolRating(Score.of(3.2232), rater, politician);

		politician.rate(actualRating);

		assertThat(politician.average().averageRating())
				.isEqualTo(EXPECTED_AVERAGE_RATING);
	}

}