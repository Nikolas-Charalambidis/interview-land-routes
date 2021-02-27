package com.nikolascharalambidis.interview.landroutes.data;

import java.util.Set;

public enum Region {

	AFRICA,
	AMERICAS,
	ANTARCTIC,
	ASIA,
	EUROPE,
	OCEANIA;

	private static final Set<Region> CONTINENTAL = Set.of(AFRICA, ASIA, EUROPE);

	public boolean connectedWith(Region region) {
		if (this == region) {
			return true;
		} else return (CONTINENTAL.contains(this) && CONTINENTAL.contains(region));
	}
}
