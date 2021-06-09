package com.cowin.lambda.model;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {
	
	private List<Centers> centers;

}
