package ski.alto.cockpit.server.repository;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ski.alto.cockpit.server.model.GateUsageDTO;

@Component
public class GateUsageDAO {
	Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	@Autowired
    JdbcTemplate jdbcTemplate;
	
	public List<GateUsageDTO> getGateUsagesByCardNumberAndResortIdForDateRange(String start_date, String end_date, Integer resort_id, String ownership) {
		List<GateUsageDTO> activities = null;
		logger.info("getGateUsagesByCardNumberAndResortIdForDateRange");

		boolean compareWithDate = !(start_date == null || start_date.equals("null") || end_date == null || end_date.equals("null"));

		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();

		if (resort_id != -1) {
			args.add(resort_id);
			argTypes.add(Types.INTEGER);
		}

		if (compareWithDate) {
			logger.info("RETURN FIRST 100");
			args.add(start_date);
			argTypes.add(Types.DATE);
			args.add(end_date);
			argTypes.add(Types.DATE);
		}

		if (ownership != null) {
			args.add(ownership);
			argTypes.add(Types.VARCHAR);
		}

		int[] argTypesInt = argTypes.stream().mapToInt(Integer::intValue).toArray();

		activities = jdbcTemplate.query(
				"select distinct lift_number, lift_name, \r\n"
						+ "count(date(activity_date)) number_of_usages, date(activity_date) usage_date \r\n"
						+ "from usage_activities ua join smart_cards sc \r\n"
						+ "on ua.smart_card_id=sc.id \r\n"
						+ "join users u \r\n"
						+ "on sc.user_id=u.id \r\n"
						+ "where 1=1 \r\n"
						+ (resort_id != -1 ? "and ua.resort_id=? \r\n" : "")
						+ (compareWithDate ? "and date(activity_date) between date(?) and date(?) \r\n" : "")
						+ (ownership != null ? "and ownership=? \r\n" : "")
						+ "group by lift_number, lift_name, date(activity_date)\r\n"
						+ "order by date(activity_date) desc\r\n"
						+ (!compareWithDate ? "limit 100" : "")
						,
				args.toArray(), argTypesInt,
				(rs, rowNum) -> new GateUsageDTO(
						rs.getString("usage_date"),
						rs.getString("lift_number"),
						rs.getString("lift_name"),
						rs.getInt("number_of_usages"))
		);

    	return activities;
    }
}
