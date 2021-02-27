package com.nikolascharalambidis.interview.landroutes.support.graph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.nikolascharalambidis.interview.landroutes.data.Country;
import com.nikolascharalambidis.interview.landroutes.data.Region;
import com.nikolascharalambidis.interview.landroutes.exception.NoPathException;

class BreadthFirstSearchTest {

	private static final Map<String, Country> COUNTRY_MAP = new HashMap<>();

	static {
		COUNTRY_MAP.put("e1", new Country("e1", Region.EUROPE, List.of("e2", "e3")));
		COUNTRY_MAP.put("e2", new Country("e2", Region.EUROPE, List.of("e1", "e3", "a1")));
		COUNTRY_MAP.put("e3", new Country("e3", Region.EUROPE, List.of("e1", "e2", "a2")));
		COUNTRY_MAP.put("e4", new Country("e4", Region.EUROPE, List.of()));
		COUNTRY_MAP.put("a1", new Country("a1", Region.ASIA, List.of("e2", "a3")));
		COUNTRY_MAP.put("a2", new Country("a2", Region.ASIA, List.of("e3", "a4")));
		COUNTRY_MAP.put("a3", new Country("a3", Region.ASIA, List.of("a1", "a4")));
		COUNTRY_MAP.put("a4", new Country("a4", Region.ASIA, List.of("a2", "a3", "a5")));
		COUNTRY_MAP.put("a5", new Country("a5", Region.ASIA, List.of("a4", "a6")));
		COUNTRY_MAP.put("a6", new Country("a6", Region.ASIA, List.of("a5")));
		COUNTRY_MAP.put("o1", new Country("o1", Region.ASIA, List.of("o2", "o3")));
		COUNTRY_MAP.put("o2", new Country("o2", Region.ASIA, List.of("o1", "o3")));
		COUNTRY_MAP.put("o3", new Country("o3", Region.ASIA, List.of("o1", "o2")));
		COUNTRY_MAP.put("o4", new Country("o4", Region.ASIA, List.of()));
	}

	@Test
	void testSameOriginAndDestination() {
		var country = COUNTRY_MAP.get("e1");
		Assertions.assertEquals(
			List.of("e1"),
			new BreadthFirstSearch(COUNTRY_MAP, country, country).paths());
	}

	@Test
	void testDirectNeighbourPath() {
		var e1 = COUNTRY_MAP.get("e1");
		var e2 = COUNTRY_MAP.get("e2");
		Assertions.assertEquals(
			List.of("e1", "e2"),
			new BreadthFirstSearch(COUNTRY_MAP, e1, e2).paths());
	}

	@Test
	void testDistantNeighbourPath_singleContinent() {
		var e1 = COUNTRY_MAP.get("e1");
		var e3 = COUNTRY_MAP.get("e3");
		Assertions.assertEquals(
			List.of("e1", "e3"),
			new BreadthFirstSearch(COUNTRY_MAP, e1, e3).paths());
	}

	@Test
	void testDistantNeighbourPath_multipleContinents() {
		var e1 = COUNTRY_MAP.get("e1");
		var a6 = COUNTRY_MAP.get("a6");
		Assertions.assertEquals(
			List.of("e1", "e3", "a2", "a4", "a5", "a6"),
			new BreadthFirstSearch(COUNTRY_MAP, e1, a6).paths());
	}

	@Test
	void testIsolatedCountry() {
		var o1 = COUNTRY_MAP.get("o1");
		var o4 = COUNTRY_MAP.get("o4");
		var search = new BreadthFirstSearch(COUNTRY_MAP, o1, o4);
		Assertions.assertThrows(NoPathException.class, search::paths);
	}
}