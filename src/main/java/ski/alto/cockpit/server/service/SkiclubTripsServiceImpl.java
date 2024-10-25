package ski.alto.cockpit.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ski.alto.cockpit.server.model.SkiclubTrips;
import ski.alto.cockpit.server.model.SkiclubTripsCounts;
import ski.alto.cockpit.server.repository.SkiclubTripsDAO;

@Service
public class SkiclubTripsServiceImpl implements SkiclubTripsService{
	
	@Autowired
	SkiclubTripsDAO repository;
	
	
	@Override
	public List<SkiclubTripsCounts> getTripsMonthlyGrouped(Integer resort_id) {
		return repository.getSkiclubTripsMonthlyGrouped(resort_id);
	}
	
	
	@Override
	public List<SkiclubTrips> getMonthlyTrips(Integer resort_id, String year, String month) {
		return repository.getSkiclubTripsForMonth(year, month, resort_id);
	}

}
