package com.cowin.lambda.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Centers {
	
	private String center_id;
	
	private String name;
	
	private String address;
	
	private String state_name;
	
	private String district_name;
	
	private String block_name;
	
	private String pincode;
	
	private String lat;
	
	private String lon;
	
	private String from;
	
	private String to;
	
	private String fee_type;
	
	private List<Sessions> sessions;

	private String date;
	
	private Integer available_capacity;
	
	private Integer min_age_limit;
	
	private String vaccine;

}
