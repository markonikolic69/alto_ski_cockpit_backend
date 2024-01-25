package ski.alto.cockpit.server.repository;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ski.alto.cockpit.server.model.ActivityDTO;

@Component
public class ActivityDAO {
	Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	@Autowired
    JdbcTemplate jdbcTemplate;
	public List<ActivityDTO> getDTACardByCardNumberAndResortIdForDateRange(String card_number, String start_date, String end_date, Integer resort_id, Integer smart_card_id) {
		List<ActivityDTO> activities = null;
		logger.info("getDTACardByCardNumberAndResortForDateRange");
		if(start_date == null || start_date.equals("null") || end_date == null || end_date.equals("null")) {
			logger.info("RETURN FIRST 100");
			activities =  jdbcTemplate.query(
					"select ua.resort_name resort_name, sc.first_name first_name, sc.last_name last_name, TO_CHAR(ua.activity_date,'YYYY-MM-DD HH24:MI:SS') activity_date, \n" +
							"ua.reader_name reader_name, ua.block_number block_number, ua.lift_number lift_number, ua.lift_name lift_name, ua.reader_number reader_number, sc.user_id user_id \n" +
							"from usage_activities ua JOIN smart_cards sc on sc.id = ua.smart_card_id \n" +
							"where ua.smart_card_id=?\n" +
							"order by ua.activity_date desc;",
	                (rs, rowNum) -> new ActivityDTO(
							rs.getString("resort_name"),
	                		card_number,
	                		rs.getString("activity_date"),
	                        rs.getString("reader_name"),
	                        rs.getString("block_number"),
	                        rs.getString("lift_number"),
	                        rs.getString("lift_name"),
	                        rs.getString("reader_number"),
	                        rs.getString("first_name"),
	                        rs.getString("last_name"),
							rs.getInt("user_id")),
	                smart_card_id
	        );
		} else if(resort_id == 0) {
			logger.info("RESORT NAME IS NULL");
	    	 activities =  jdbcTemplate.query(
					 "select ua.resort_name resort_name, sc.first_name first_name, sc.last_name last_name, TO_CHAR(ua.activity_date,'YYYY-MM-DD HH24:MI:SS') activity_date, \n" +
							 "ua.reader_name reader_name, ua.block_number block_number, ua.lift_number lift_number, ua.lift_name lift_name, ua.reader_number reader_number, sc.user_id user_id\n" +
							 "from usage_activities ua JOIN smart_cards sc on sc.id = ua.smart_card_id \n" +
							 "where ua.activity_date::date between date(?) and date(?) \n" +
							 "and ua.smart_card_id=?\n" +
							 "order by ua.activity_date desc;",
	                (rs, rowNum) -> new ActivityDTO(
							rs.getString("resort_name"),
	                		card_number,
	                		rs.getString("activity_date"),
	                        rs.getString("reader_name"),
	                        rs.getString("block_number"),
	                        rs.getString("lift_number"),
	                        rs.getString("lift_name"),
	                        rs.getString("reader_number"),
	                        rs.getString("first_name"),
	                        rs.getString("last_name"),
							rs.getInt("user_id")),
	                start_date, end_date, smart_card_id
	        );
		}
		else
			activities =  jdbcTemplate.query(
	                "select ua.resort_name resort_name, sc.first_name first_name, sc.last_name last_name, TO_CHAR(ua.activity_date,'YYYY-MM-DD HH24:MI:SS') activity_date, \n" +
							"ua.reader_name reader_name, ua.block_number block_number, ua.lift_number lift_number, ua.lift_name lift_name, ua.reader_number reader_number, sc.user_id user_id\n" +
							"from usage_activities ua JOIN smart_cards sc on sc.id = ua.smart_card_id \n" +
							"where ua.activity_date between date(?) and date(?) \n" +
							"and ua.resort_id=? and ua.smart_card_id=?\n" +
							"order by ua.activity_date desc;",
	                (rs, rowNum) -> new ActivityDTO(
							rs.getString("resort_name"),
	                		card_number,
	                		rs.getString("activity_date"),
	                        rs.getString("reader_name"),
	                        rs.getString("block_number"),
	                        rs.getString("lift_number"),
	                        rs.getString("lift_name"),
	                        rs.getString("reader_number"),
	                        rs.getString("first_name"),
	                        rs.getString("last_name"),
							rs.getInt("user_id")),
	                start_date, end_date, resort_id, smart_card_id
	        );
    	
    	return activities;
    }
	
	public List<ActivityDTO> getAllDTACardActivitiesForResortIdForToday(String todays_date, Integer resort_id, String ownership) {
		List<ActivityDTO> activities = null;
		logger.info("getAllDTACardActivitiesForResortIdForToday");

		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();

		args.add(todays_date);
		argTypes.add(Types.DATE);

		if (resort_id != -1) {
			args.add(resort_id);
			argTypes.add(Types.INTEGER);
		}

		if (ownership != null) {
			args.add(ownership);
			argTypes.add(Types.VARCHAR);
		}

		int[] argTypesInt = argTypes.stream().mapToInt(Integer::intValue).toArray();

		activities =  jdbcTemplate.query(
					"select ua.resort_name resort_name, sc.first_name first_name, sc.last_name last_name, sc.card_number card_number, TO_CHAR(ua.activity_date,'YYYY-MM-DD HH24:MI:SS') activity_date, "
							+ "ua.reader_name reader_name, ua.block_number block_number, "
							+ "ua.lift_number lift_number, ua.lift_name lift_name, ua.reader_number reader_number, sc.user_id user_id\r\n"
							+ "from usage_activities ua join smart_cards sc \r\n"
							+ "on ua.smart_card_id=sc.id \r\n"
							+ "join users u \r\n"
							+ "on sc.user_id=u.id \r\n"
							+ "where TO_CHAR(ua.activity_date,'YYYY-MM-DD')=? \r\n"
							+ (resort_id != -1 ? "and ua.resort_id=? \r\n" : "")
							+ (ownership != null ? "and ownership=? \r\n" : "")
							+ "order by ua.activity_date desc",
					args.toArray(), argTypesInt,
					(rs, rowNum) -> new ActivityDTO(
							rs.getString("resort_name"),
							rs.getString("card_number"),
							rs.getString("activity_date"),
							rs.getString("reader_name"),
							rs.getString("block_number"),
							rs.getString("lift_number"),
							rs.getString("lift_name"),
							rs.getString("reader_number"),
							rs.getString("first_name"),
							rs.getString("last_name"),
							rs.getInt("user_id"))
			);

    	return activities;
    }
}
