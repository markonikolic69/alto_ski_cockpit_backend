package ski.alto.cockpit.server.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ski.alto.cockpit.server.controller.request.SkiclubTripsGroupRequest;
import ski.alto.cockpit.server.controller.request.SkiclubTripsRequest;
import ski.alto.cockpit.server.model.SkiclubTrips;
import ski.alto.cockpit.server.model.SkiclubTripsCounts;
import ski.alto.cockpit.server.service.SkiclubTripsService;

@RestController
public class SkiclubTripsController {

	Logger logger = LoggerFactory.getLogger(this.getClass().getName());


	@Autowired
	SkiclubTripsService service;

	@PostMapping("/tripsMonthGrouped")
	@CrossOrigin(origins = {"http://94.127.4.240:4200", "http://reciklomatmobapp.suprabit.rs:8081/", "http://localhost:4200", "http://localhost:8081", "https://cockpit.alto.ski", "http://65.21.206.110:8081", "https://skiclubgb-cockpit-client-ec942f8fa647.herokuapp.com", "https://skiclub.alto.ski", "https://alto-ski-cockpit-client-393cf5cd662a.herokuapp.com"})
	public List<SkiclubTripsCounts> getTripsMonhlyGrouped(@RequestBody SkiclubTripsGroupRequest request) {
		logger.info("request = " + request);
		
		return service.getTripsMonthlyGrouped(request.getResortId());

	}
	
	@PostMapping("/tripsMonthDetails")
	@CrossOrigin(origins = {"http://94.127.4.240:4200", "http://reciklomatmobapp.suprabit.rs:8081/", "http://localhost:4200", "http://localhost:8081", "https://cockpit.alto.ski", "http://65.21.206.110:8081", "https://skiclubgb-cockpit-client-ec942f8fa647.herokuapp.com", "https://skiclub.alto.ski", "https://alto-ski-cockpit-client-393cf5cd662a.herokuapp.com"})
	public List<SkiclubTrips> getTripsMonthlyGrouped(@RequestBody SkiclubTripsRequest request) {
		logger.info("request = " + request);
		
		return service.getMonthlyTrips(request.getResortId(), request.getYear(), request.getMonth());

	}

}
