package com.nikolascharalambidis.interview.landroutes.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nikolascharalambidis.interview.landroutes.client.mledoze.CountryClient;
import com.nikolascharalambidis.interview.landroutes.data.Country;
import com.nikolascharalambidis.interview.landroutes.mapper.CountryMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CountryServiceImpl implements CountryService {

	private final CountryClient countryClient;
	private final CountryMapper countryMapper;

	@Override
	public List<Country> countries() {
		var countryDtoList = countryClient.countries();
		return countryMapper.fromDto(countryDtoList);
	}
}
