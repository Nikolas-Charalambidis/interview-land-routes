package com.nikolascharalambidis.interview.landroutes.delegate;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.nikolascharalambidis.interview.landroutes.api.routing.api.RoutingApi;
import com.nikolascharalambidis.interview.landroutes.api.routing.model.Route;
import com.nikolascharalambidis.interview.landroutes.service.RoutingService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@AllArgsConstructor
public class RoutingDelegate implements RoutingApi {

	private final RoutingService routingService;

	@Override
	public ResponseEntity<Route> originDestination(String origin, String destination) {
		var route = routingService.route(origin, destination);
		return ResponseEntity.ok(route);
	}
}
