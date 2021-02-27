package com.nikolascharalambidis.interview.landroutes.service;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.nikolascharalambidis.interview.landroutes.api.routing.model.Route;
import com.nikolascharalambidis.interview.landroutes.data.Country;
import com.nikolascharalambidis.interview.landroutes.exception.NoPathException;
import com.nikolascharalambidis.interview.landroutes.support.graph.BreadthFirstSearch;

import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Service
public class RoutingServiceImpl implements RoutingService {

	private final CountryService countryService;

	@Override
	public Route route(String origin, String destination) {

		var countries = countryService.countries().stream()
			.collect(Collectors.toMap(Country::getName, Function.identity()));

		var originCountry = Optional.ofNullable(countries.get(origin))
			.orElseThrow(() -> new NoPathException(String.format("Unknown origin country %s", origin)));

		var destinationCountry = Optional.ofNullable(countries.get(destination))
			.orElseThrow(() -> new NoPathException(String.format("Unknown destination country %s", destination)));

		if (!originCountry.getRegion().connectedWith(destinationCountry.getRegion())) {
			throw new NoPathException(String.format(
				"%s (%s) is not connected with %s (%s) by land",
				originCountry.getRegion(), origin,
				destinationCountry.getRegion(), destination));
		}

		if (!origin.equals(destination)) {
			if (originCountry.getBorders().isEmpty()) {
				throw new NoPathException(String.format("Origin %s is isolated", origin));
			}

			if (destinationCountry.getBorders().isEmpty()) {
				throw new NoPathException(String.format("Destination %s is isolated", destination));
			}
		}

		var routes = new BreadthFirstSearch(countries, originCountry, destinationCountry).paths();

		var route = new Route();
		route.setRoutes(routes);
		return route;
	}
}
