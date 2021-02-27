package com.nikolascharalambidis.interview.landroutes.service;

import com.nikolascharalambidis.interview.landroutes.api.routing.model.Route;

public interface RoutingService {

	Route route(String origin, String destination);
}
