package com.cowin.lambda.config;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.cowin.lambda.model.Response;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

// Handler value: example.Handler
public class Handler implements RequestHandler<Object, String> {

	private static final Logger logger = LoggerFactory.getLogger(Handler.class);

	private RestTemplate restTemplate;

	final String ROOT_URI = "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/calendarByDistrict?district_id=568&date=";

	public Handler() {
		super();
		this.restTemplate = new RestTemplate();
	}

	@Override
	public String handleRequest(Object event, Context context) {
		// call Lambda API
		logger.info("Started processing vaccine api code");

		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("mm-dd-yyyy");
		String strDate = dateFormat.format(date);
		String availability = checkAvailability(strDate);
		if (StringUtils.isEmpty(availability)) {
			Date dt = new Date();
			Calendar c = Calendar.getInstance();
			c.setTime(dt);
			c.add(Calendar.DATE, 1);
			dt = c.getTime();
			String dtDate = dateFormat.format(date);
			String nextAvailability = checkAvailability(dtDate);
			if (StringUtils.isEmpty(nextAvailability)) {
				throw new RuntimeException("no slots available");
			}
			return nextAvailability;
		}
		else {
			return availability;
		}

	}

	public String checkAvailability(final String date) {
		ResponseEntity<Response> response = restTemplate.getForEntity(ROOT_URI + "/" + date, Response.class);

		Response res = response.getBody();

		List<String> availability = new ArrayList<>();
		if (null != res.getCenters()) {
			res.getCenters().forEach(center -> {
				if (center.getAvailable_capacity() > 0) {
					center.getSessions().forEach(session -> {
						if (session.getAvailable_capacity_dose1() > 0) {
							availability.add("First dose slot available in " + center.getName() + ", count="
									+ session.getAvailable_capacity_dose1());
						}
					});
				}
			});
		}

		if (!availability.isEmpty()) {
			logger.info("slots available");
			return availability.stream().map(Object::toString).collect(Collectors.joining("\n"));
		}
		else {
			logger.info("no slots available");
			return null;
		}
	}
}
