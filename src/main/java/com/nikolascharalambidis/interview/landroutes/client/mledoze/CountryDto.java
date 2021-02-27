package com.nikolascharalambidis.interview.landroutes.client.mledoze;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryDto {

	private String cca3;
	private String region;
	private List<String> borders;
}
