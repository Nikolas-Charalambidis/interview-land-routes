package com.nikolascharalambidis.interview.landroutes.data;

import java.util.List;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Country {

	private final String name;
	private final Region region;
	private final List<String> borders;

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Country)) {
			return false;
		}
		final Country country = (Country) o;
		return Objects.equals(getName(), country.getName());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getName());
	}
}
