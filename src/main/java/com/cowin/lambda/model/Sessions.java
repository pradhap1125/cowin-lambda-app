package com.cowin.lambda.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Sessions {

	private String session_id;

	private String date;
	
	private Integer available_capacity;
	
	private Integer min_age_limit;
	
	private String vaccine;
	
	private List<String> slots;
	
	private Integer available_capacity_dose1;
	
	private Integer available_capacity_dose2;

}
