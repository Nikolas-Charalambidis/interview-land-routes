package com.nikolascharalambidis.interview.landroutes.mapper;

import java.util.Arrays;
import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.nikolascharalambidis.interview.landroutes.client.mledoze.CountryDto;
import com.nikolascharalambidis.interview.landroutes.data.Country;
import com.nikolascharalambidis.interview.landroutes.data.Region;

@Mapper(componentModel = "spring")
public interface CountryMapper {

	@Mapping(target = "name", source = "cca3")
	@Mapping(target = "region", qualifiedByName = "regionMapping")
	Country fromDto(CountryDto countryDto);

	@IterableMapping(elementTargetType  = Country.class)
	List<Country> fromDto(List<CountryDto> countryDtoList);

	@Named("regionMapping")
	default Region stringToRegion(String region) {
		return Arrays.stream(Region.values())
			.filter(r -> r.name().equalsIgnoreCase(region))
			.findFirst()
			.orElse(null);
	}
}
