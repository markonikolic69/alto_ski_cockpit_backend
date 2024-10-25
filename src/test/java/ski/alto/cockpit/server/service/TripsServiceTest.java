package ski.alto.cockpit.server.service;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ski.alto.cockpit.server.model.SkiclubTrips;
import ski.alto.cockpit.server.model.SkiclubTripsCounts;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TripsServiceTest {
	
	
	@Autowired
	SkiclubTripsService tripsService;
	
	
	 @Test
	 void getTripsGroupedMonthly() {

		 
		 List<SkiclubTrips> trips_grupno = tripsService.getMonthlyTrips(1371, "2024", "12");
//	        AdminCockpitUsersDTO user = adminCockpitUsersService.findByEmailAndPasswordDigest(
//	                "manager@test.com",
//	                "1d0258c2440a8d19e716292b231e3190"
//	        );

	      assertNotNull(trips_grupno);
	      System.out.println("#######trips_grupno = " + trips_grupno);
	 }
	 
	 
	 @Test
	 void getTripsDetails() {

		 
		 List<SkiclubTripsCounts> trips = tripsService.getTripsMonthlyGrouped(1371);
//	        AdminCockpitUsersDTO user = adminCockpitUsersService.findByEmailAndPasswordDigest(
//	                "manager@test.com",
//	                "1d0258c2440a8d19e716292b231e3190"
//	        );

	      assertNotNull(trips);
	      System.out.println("#########trips = " + trips);
	 }

}
