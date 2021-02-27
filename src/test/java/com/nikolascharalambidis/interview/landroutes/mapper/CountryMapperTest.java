package com.nikolascharalambidis.interview.landroutes.mapper;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.nikolascharalambidis.interview.landroutes.client.mledoze.CountryDto;
import com.nikolascharalambidis.interview.landroutes.data.Country;

@SpringBootTest
class CountryMapperTest {

	@Autowired
	private CountryMapper mapper;

	@Test
	void testFromDto_oneObject() {
		var countryDto = new CountryDto("one", "Asia", List.of("two", "three"));
		var country = mapper.fromDto(countryDto);

		assertCountry(countryDto, country);
	}

	@Test
	void testFromDto_list() {

		var countryDtoList= List.of(
			new CountryDto("one", "Asia", List.of("two", "three")),
			new CountryDto("two", "aSIa", List.of("one", "three")),
			new CountryDto("three", "ASIA", List.of("one", "two")),
			new CountryDto("four", "asia", List.of()));

		var countries = mapper.fromDto(countryDtoList);

		for (int i = 0; i < 4; i++) {
			assertCountry(countryDtoList.get(0), countries.get(0));
		}
	}

	@Test
	void testFromDto_invalidRegion() {
		var countryDto = new CountryDto("one", "Blah", List.of());
		var country = mapper.fromDto(countryDto);

		Assertions.assertNull(country.getRegion());
	}

	static void assertCountry(CountryDto expected, Country actual) {
		Assertions.assertEquals(expected.getCca3(), actual.getName());
		Assertions.assertEquals(expected.getBorders(), actual.getBorders());
		Assertions.assertNotNull(actual.getRegion());
		Assertions.assertEquals(expected.getRegion().toUpperCase(), actual.getRegion().name());
	}
}