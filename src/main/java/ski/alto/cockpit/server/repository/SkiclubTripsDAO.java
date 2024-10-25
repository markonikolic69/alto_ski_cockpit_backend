package ski.alto.cockpit.server.repository;

import org.springframework.stereotype.Component;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;


import ski.alto.cockpit.server.model.SkiclubTrips;
import ski.alto.cockpit.server.model.SkiclubTripsCounts;

@Component
public class SkiclubTripsDAO {
	
	Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	
	public List<SkiclubTrips> getSkiclubTripsForMonth(String from_year, String from_month, Integer resort_id) {
		List<SkiclubTrips> trips = null;
		logger.info("getSkiclubTripsForMonth");

		trips =  jdbcTemplate.query(
					"select u.first_name first_name, u.last_name last_name, u.id user_id, t.starts_at starts_at, t.ends_at ends_at, u.birth_date as dob, u.email as email, t.confirmed_at as confirmed_at " + //, t.confirmed_at confirmed_at \n" +
							"from users u JOIN schedules t on u.id = t.user_id \n" +
							"where t.location_id = ? and to_char(t.starts_at, 'yyyy') = ? and to_char(t.starts_at, 'MM') = ? \n" +
							"order by t.starts_at desc;",
							(rs, rowNum) -> new SkiclubTrips(
									rs.getString("starts_at"),
									rs.getString("ends_at"),
									rs.getString("confirmed_at") != null,
									rs.getString("first_name"),
									rs.getString("last_name"),
									rs.getInt("user_id"),
									resort_id,
									(rs.getString("dob") == null || rs.getString("dob").length() < 10) ? "" : rs.getString("dob").substring(0,10),
									rs.getString("email")),
							resort_id, from_year, from_month
					);
		return trips;
	}
	
	public List<SkiclubTripsCounts> getSkiclubTripsMonthlyGrouped(Integer resort_id) {
		List<SkiclubTripsCounts> trips = null;
		logger.info("getSkiclubTripsForMonth");

		trips =  jdbcTemplate.query(
					"select to_char(starts_at, 'MM') as month, \n" + 
					"CASE \n" + 
					"  WHEN to_char(starts_at, 'MM') = '01' THEN 'JAN' \n" + 
					"  WHEN to_char(starts_at, 'MM') = '02' THEN 'FEB' \n" + 
					"  WHEN to_char(starts_at, 'MM') = '03' THEN 'MAR' \n" + 
					"  WHEN to_char(starts_at, 'MM') = '04' THEN 'APR' \n" + 
					"  WHEN to_char(starts_at, 'MM') = '05' THEN 'MAJ' \n" + 
					"  WHEN to_char(starts_at, 'MM') = '06' THEN 'JUN' \n" + 
					"  WHEN to_char(starts_at, 'MM') = '07' THEN 'JUL' \n" + 
					"  WHEN to_char(starts_at, 'MM') = '08' THEN 'AVG' \n" + 
					"  WHEN to_char(starts_at, 'MM') = '09' THEN 'SEP' \n" + 
					"  WHEN to_char(starts_at, 'MM') = '10' THEN 'OCT' \n" + 
					"  WHEN to_char(starts_at, 'MM') = '11' THEN 'NOV' \n" + 
					"ELSE \n" + 
					"  'DEC' \n" + 
					"END as month_string, \n" + 
					"to_char(starts_at, 'yyyy') as year, count(id) as number,  count(confirmed_at) as confirmed from schedules where \n" + 
					"location_id = ? \n" + 
					"group by to_char(starts_at, 'MM'), to_char(starts_at, 'yyyy') order by to_char(starts_at, 'yyyy') desc , to_char(starts_at, 'MM') desc;",
							(rs, rowNum) -> new SkiclubTripsCounts(
									rs.getString("month"),
									rs.getString("month_string"),
									rs.getString("year"),
									rs.getInt("number"),
									resort_id,
									rs.getInt("confirmed")),
							resort_id
					);
		return trips;
		
	}

}
