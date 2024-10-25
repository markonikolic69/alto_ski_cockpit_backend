package ski.alto.cockpit.server.service;

import java.util.List;


import ski.alto.cockpit.server.model.SkiclubTrips;
import ski.alto.cockpit.server.model.SkiclubTripsCounts;

public interface SkiclubTripsService {
	
	
	
	
	
	public List<SkiclubTripsCounts> getTripsMonthlyGrouped(Integer resort_id);
	public List<SkiclubTrips> getMonthlyTrips(Integer resort_id, String year, String month);

}
