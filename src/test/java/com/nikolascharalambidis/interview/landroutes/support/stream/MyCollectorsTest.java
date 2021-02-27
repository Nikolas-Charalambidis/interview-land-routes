package com.nikolascharalambidis.interview.landroutes.support.stream;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lombok.AllArgsConstructor;
import lombok.Data;

class MyCollectorsTest {

	@Test
	void testEmpty() {
		Assertions.assertTrue(
			Stream.empty()
				.collect(MyCollectors.reversing())
				.isEmpty());
	}

	@Test
	void testIntegers() {
		Assertions.assertEquals(
			List.of(3, 2, 1),
			List.of(1, 2, 3)
				.stream()
				.collect(MyCollectors.reversing()));
	}

	@Test
	void testObjects() {
		Assertions.assertEquals(
			List.of(new Pojo(3, "three"), new Pojo(2, "two"), new Pojo(1, "one")),
			List.of(new Pojo(1, "one"), new Pojo(2, "two"), new Pojo(3, "three"))
				.stream()
				.collect(MyCollectors.reversing()));
	}

	@Data
	@AllArgsConstructor
	static class Pojo {
		int id;
		String name;
	}
}