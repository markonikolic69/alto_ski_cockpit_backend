package ski.alto.cockpit.server.repository;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ski.alto.cockpit.server.model.AdminUsersPreviewDTO;
import ski.alto.cockpit.server.model.DTACardDTO;
import ski.alto.cockpit.server.model.PromoCodeRedemptionDTO;
import ski.alto.cockpit.server.model.UserDTO;

@Component
public class UserDAO {
	@Autowired
    JdbcTemplate jdbcTemplate;
	
	private List<DTACardDTO> getAssignedCards(int user_id) {
		List<DTACardDTO> dtaCards =  jdbcTemplate.query(
                "select sc.id, sc.card_number, TO_CHAR(sc.record_expires,'YYYY-MM-DD') record_expires,TO_CHAR(sc.birth_date,'YYYY-MM-DD') birth_date, status, sc.first_name, "
                + "sc.last_name, sc.image image from smart_cards sc join users u on sc.user_id=u.id and "
                + "(sc.first_name != u.first_name or sc.last_name != u.last_name)\r\n"
                + "where sc.user_id=?",
                (rs, rowNum) -> new DTACardDTO(
						rs.getInt("id"),
                		rs.getString("card_number"),
						rs.getString("record_expires"),
						rs.getString("birth_date"),
                        rs.getString("status"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("image")),
                user_id
        );
    	
    	return dtaCards;
	}
	
	private List<DTACardDTO> getOwnedCards(int user_id) {
		List<DTACardDTO> dtaCards =  jdbcTemplate.query(
                "select sc.id, sc.card_number, TO_CHAR(sc.record_expires,'YYYY-MM-DD') record_expires, TO_CHAR(sc.birth_date,'YYYY-MM-DD') birth_date, status, sc.first_name, "
                + "sc.last_name, sc.image image from smart_cards sc join users u on sc.user_id=u.id and "
                + "(LOWER(sc.first_name) = LOWER(u.first_name) and LOWER(sc.last_name) = LOWER(u.last_name))\r\n"
                + "where sc.user_id=?",
                (rs, rowNum) -> new DTACardDTO(
						rs.getInt("id"),
                		rs.getString("card_number"),
						rs.getString("record_expires"),
						rs.getString("birth_date"),
                        rs.getString("status"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("image")),
                user_id
        );
    	
    	return dtaCards;
	}

	private List<PromoCodeRedemptionDTO> getPromoCodeRedemptions(int user_id) {
		List<PromoCodeRedemptionDTO> promoCodeRedemptions =  jdbcTemplate.query(
				"select code, pcr.created_at redemption_time from promo_codes pc, promo_code_redemptions pcr " +
						"where pc.id = pcr.promo_code_id " +
						"and pcr.user_id = ?",
				(rs, rowNum) -> new PromoCodeRedemptionDTO(
						rs.getString("code"),
						rs.getString("redemption_time")),
				user_id
		);

		return promoCodeRedemptions;
	}
	
	private List<String> getOtherCardNumbers(int user_id) {
		return jdbcTemplate.query(
                "select sc.card_number from smart_cards sc join users u on sc.user_id=u.id and (sc.first_name != u.first_name or sc.last_name != u.last_name)\r\n"
                + "where sc.user_id=?",
                (rs, rowNum) -> rs.getString("card_number"),
                user_id
        );
	}

    public List<UserDTO> getUserByFirstName(String first_name, String ownership) {

		List<UserDTO> users = null;

    	if (ownership != null) {
			users =  jdbcTemplate.query(
					"select u.id id, u.first_name first_name, u.last_name last_name, u.email email,\r\n"
							+ " u.phone phone, u.address1 address1, u.address2 address2, u.city city, \r\n"
							+ "u.country country, u.county_or_state county_or_state, u.postal_code postal_code, \r\n"
//                + "DATE(sc.record_expires) > DATE(CURRENT_TIMESTAMP) credit_card_registered, \r\n"
							+ "(u.card_id IS NOT NULL and u.card_id <> '') credit_card_registered, \r\n"
							+ "CASE \r\n"
							+ "	when (select TO_CHAR(u.birth_date,'YYYY-MM-DD')) != '' then (select TO_CHAR(u.birth_date,'YYYY-MM-DD')) \r\n"
							+ "	when (select TO_CHAR(sc.birth_date,'YYYY-MM-DD')) != '' then (select TO_CHAR(sc.birth_date,'YYYY-MM-DD')) \r\n"
							+ "END birth_date, \r\n"
							+ "u.role user_role, \r\n"
							+ "u.ownership ownership \r\n"
							+ "from smart_cards sc right outer join users u on u.id = sc.user_id\r\n"
							+ "and LOWER(sc.first_name) = LOWER(u.first_name) and LOWER(sc.last_name) = LOWER(u.last_name)\r\n"
							+ "where LOWER(u.first_name) LIKE LOWER('%'||?||'%')\r\n"
							+ "and u.ownership=?",
					(rs, rowNum) -> new UserDTO(
							rs.getInt("id"),
							rs.getString("first_name"),
							rs.getString("last_name"),
							rs.getString("birth_date"),
							rs.getString("email"),
							rs.getString("phone"),
							rs.getString("address1"),
							rs.getString("address2"),
							rs.getString("city"),
							rs.getString("country"),
							rs.getString("county_or_state"),
							rs.getString("postal_code"),
							null,
							null,
							rs.getBoolean("credit_card_registered"),
							null,
							rs.getString("user_role"),
							rs.getString("ownership")),
					first_name, ownership
			);
		} else {
			users =  jdbcTemplate.query(
					"select u.id id, u.first_name first_name, u.last_name last_name, u.email email,\r\n"
							+ " u.phone phone, u.address1 address1, u.address2 address2, u.city city, \r\n"
							+ "u.country country, u.county_or_state county_or_state, u.postal_code postal_code, \r\n"
//                + "DATE(sc.record_expires) > DATE(CURRENT_TIMESTAMP) credit_card_registered, \r\n"
							+ "(u.card_id IS NOT NULL and u.card_id <> '') credit_card_registered, \r\n"
							+ "CASE \r\n"
							+ "	when (select TO_CHAR(u.birth_date,'YYYY-MM-DD')) != '' then (select TO_CHAR(u.birth_date,'YYYY-MM-DD')) \r\n"
							+ "	when (select TO_CHAR(sc.birth_date,'YYYY-MM-DD')) != '' then (select TO_CHAR(sc.birth_date,'YYYY-MM-DD')) \r\n"
							+ "END birth_date, \r\n"
							+ "u.role user_role, \r\n"
							+ "u.ownership ownership \r\n"
							+ "from smart_cards sc right outer join users u on u.id = sc.user_id\r\n"
							+ "and LOWER(sc.first_name) = LOWER(u.first_name) and LOWER(sc.last_name) = LOWER(u.last_name)\r\n"
							+ "where LOWER(u.first_name) LIKE LOWER('%'||?||'%')",
					(rs, rowNum) -> new UserDTO(
							rs.getInt("id"),
							rs.getString("first_name"),
							rs.getString("last_name"),
							rs.getString("birth_date"),
							rs.getString("email"),
							rs.getString("phone"),
							rs.getString("address1"),
							rs.getString("address2"),
							rs.getString("city"),
							rs.getString("country"),
							rs.getString("county_or_state"),
							rs.getString("postal_code"),
							null,
							null,
							rs.getBoolean("credit_card_registered"),
							null,
							rs.getString("user_role"),
							rs.getString("ownership")),
					first_name
			);
		}
    	
    	for (UserDTO userDTO : users) {
    		userDTO.setOwned_cards(getOwnedCards(userDTO.getId()));
		}
    	
    	for (UserDTO userDTO : users) {
    		userDTO.setAssigned_cards(getAssignedCards(userDTO.getId()));
		}

		for (UserDTO userDTO : users) {
			userDTO.setPromo_code_redemptions(getPromoCodeRedemptions(userDTO.getId()));
		}
    	
    	return users;
    }
    
    public List<UserDTO> getUserByLastName(String last_name, String ownership) {

		List<UserDTO> users = null;

    	if (ownership != null) {
			users =  jdbcTemplate.query(
					"select u.id id, u.first_name first_name, u.last_name last_name, u.email email,\r\n"
							+ " u.phone phone, u.address1 address1, u.address2 address2, u.city city, \r\n"
							+ "u.country country, u.county_or_state county_or_state, u.postal_code postal_code, \r\n"
							+ "(u.card_id IS NOT NULL and u.card_id <> '') credit_card_registered, \r\n"
							+ "CASE \r\n"
							+ "	when (select TO_CHAR(u.birth_date,'YYYY-MM-DD')) != '' then (select TO_CHAR(u.birth_date,'YYYY-MM-DD')) \r\n"
							+ "	when (select TO_CHAR(sc.birth_date,'YYYY-MM-DD')) != '' then (select TO_CHAR(sc.birth_date,'YYYY-MM-DD')) \r\n"
							+ "END birth_date, \r\n"
							+ "u.role user_role, \r\n"
							+ "u.ownership ownership \r\n"
							+ "from smart_cards sc right outer join users u on u.id = sc.user_id\r\n"
							+ "and LOWER(sc.first_name) = LOWER(u.first_name) and LOWER(sc.last_name) = LOWER(u.last_name)\r\n"
							+ "where LOWER(u.last_name) LIKE LOWER('%'||?||'%')"
							+ "and u.ownership=?",
					(rs, rowNum) -> new UserDTO(
							rs.getInt("id"),
							rs.getString("first_name"),
							rs.getString("last_name"),
							rs.getString("birth_date"),
							rs.getString("email"),
							rs.getString("phone"),
							rs.getString("address1"),
							rs.getString("address2"),
							rs.getString("city"),
							rs.getString("country"),
							rs.getString("county_or_state"),
							rs.getString("postal_code"),
							null,
							null,
							rs.getBoolean("credit_card_registered"),
							null,
							rs.getString("user_role"),
							rs.getString("ownership")),
					last_name, ownership
			);
		} else {
			users =  jdbcTemplate.query(
					"select u.id id, u.first_name first_name, u.last_name last_name, u.email email,\r\n"
							+ " u.phone phone, u.address1 address1, u.address2 address2, u.city city, \r\n"
							+ "u.country country, u.county_or_state county_or_state, u.postal_code postal_code, \r\n"
							+ "(u.card_id IS NOT NULL and u.card_id <> '') credit_card_registered, \r\n"
							+ "CASE \r\n"
							+ "	when (select TO_CHAR(u.birth_date,'YYYY-MM-DD')) != '' then (select TO_CHAR(u.birth_date,'YYYY-MM-DD')) \r\n"
							+ "	when (select TO_CHAR(sc.birth_date,'YYYY-MM-DD')) != '' then (select TO_CHAR(sc.birth_date,'YYYY-MM-DD')) \r\n"
							+ "END birth_date, \r\n"
							+ "u.role user_role, \r\n"
							+ "u.ownership ownership \r\n"
							+ "from smart_cards sc right outer join users u on u.id = sc.user_id\r\n"
							+ "and LOWER(sc.first_name) = LOWER(u.first_name) and LOWER(sc.last_name) = LOWER(u.last_name)\r\n"
							+ "where LOWER(u.last_name) LIKE LOWER('%'||?||'%')",
					(rs, rowNum) -> new UserDTO(
							rs.getInt("id"),
							rs.getString("first_name"),
							rs.getString("last_name"),
							rs.getString("birth_date"),
							rs.getString("email"),
							rs.getString("phone"),
							rs.getString("address1"),
							rs.getString("address2"),
							rs.getString("city"),
							rs.getString("country"),
							rs.getString("county_or_state"),
							rs.getString("postal_code"),
							null,
							null,
							rs.getBoolean("credit_card_registered"),
							null,
							rs.getString("user_role"),
							rs.getString("ownership")),
					last_name
			);
		}
    	
    	for (UserDTO userDTO : users) {
    		userDTO.setOwned_cards(getOwnedCards(userDTO.getId()));
		}
    	
    	for (UserDTO userDTO : users) {
    		userDTO.setAssigned_cards(getAssignedCards(userDTO.getId()));
		}

		for (UserDTO userDTO : users) {
			userDTO.setPromo_code_redemptions(getPromoCodeRedemptions(userDTO.getId()));
		}

		return users;
    }
    
    public List<UserDTO> getUserByEmail(String email, String ownership) {

		List<UserDTO> users = null;

		if (ownership != null) {
			users =  jdbcTemplate.query(
					"select u.id id, u.first_name first_name, u.last_name last_name, u.email email,\r\n"
							+ " u.phone phone, u.address1 address1, u.address2 address2, u.city city, \r\n"
							+ "u.country country, u.county_or_state county_or_state, u.postal_code postal_code, \r\n"
							+ "(u.card_id IS NOT NULL and u.card_id <> '') credit_card_registered, \r\n"
							+ "CASE \r\n"
							+ "	when (select TO_CHAR(u.birth_date,'YYYY-MM-DD')) != '' then (select TO_CHAR(u.birth_date,'YYYY-MM-DD')) \r\n"
							+ "	when (select TO_CHAR(sc.birth_date,'YYYY-MM-DD')) != '' then (select TO_CHAR(sc.birth_date,'YYYY-MM-DD')) \r\n"
							+ "END birth_date, \r\n"
							+ "u.role user_role, \r\n"
							+ "u.ownership ownership \r\n"
							+ "from smart_cards sc right outer join users u on u.id = sc.user_id\r\n"
							+ "and LOWER(sc.first_name) = LOWER(u.first_name) and LOWER(sc.last_name) = LOWER(u.last_name)\r\n"
							+ "where LOWER(u.email) LIKE LOWER('%'||?||'%')"
							+ "and u.ownership=?",
					(rs, rowNum) -> new UserDTO(
							rs.getInt("id"),
							rs.getString("first_name"),
							rs.getString("last_name"),
							rs.getString("birth_date"),
							rs.getString("email"),
							rs.getString("phone"),
							rs.getString("address1"),
							rs.getString("address2"),
							rs.getString("city"),
							rs.getString("country"),
							rs.getString("county_or_state"),
							rs.getString("postal_code"),
							null,
							null,
							rs.getBoolean("credit_card_registered"),
							null,
							rs.getString("user_role"),
							rs.getString("ownership")),
					email, ownership
			);
		} else {
			users =  jdbcTemplate.query(
					"select u.id id, u.first_name first_name, u.last_name last_name, u.email email,\r\n"
							+ " u.phone phone, u.address1 address1, u.address2 address2, u.city city, \r\n"
							+ "u.country country, u.county_or_state county_or_state, u.postal_code postal_code, \r\n"
							+ "(u.card_id IS NOT NULL and u.card_id <> '') credit_card_registered, \r\n"
							+ "CASE \r\n"
							+ "	when (select TO_CHAR(u.birth_date,'YYYY-MM-DD')) != '' then (select TO_CHAR(u.birth_date,'YYYY-MM-DD')) \r\n"
							+ "	when (select TO_CHAR(sc.birth_date,'YYYY-MM-DD')) != '' then (select TO_CHAR(sc.birth_date,'YYYY-MM-DD')) \r\n"
							+ "END birth_date, \r\n"
							+ "u.role user_role, \r\n"
							+ "u.ownership ownership \r\n"
							+ "from smart_cards sc right outer join users u on u.id = sc.user_id\r\n"
							+ "and LOWER(sc.first_name) = LOWER(u.first_name) and LOWER(sc.last_name) = LOWER(u.last_name)\r\n"
							+ "where LOWER(u.email) LIKE LOWER('%'||?||'%')",
					(rs, rowNum) -> new UserDTO(
							rs.getInt("id"),
							rs.getString("first_name"),
							rs.getString("last_name"),
							rs.getString("birth_date"),
							rs.getString("email"),
							rs.getString("phone"),
							rs.getString("address1"),
							rs.getString("address2"),
							rs.getString("city"),
							rs.getString("country"),
							rs.getString("county_or_state"),
							rs.getString("postal_code"),
							null,
							null,
							rs.getBoolean("credit_card_registered"),
							null,
							rs.getString("user_role"),
							rs.getString("ownership")),
					email
			);
		}
    	
    	for (UserDTO userDTO : users) {
    		userDTO.setOwned_cards(getOwnedCards(userDTO.getId()));
		}
    	
    	for (UserDTO userDTO : users) {
    		userDTO.setAssigned_cards(getAssignedCards(userDTO.getId()));
		}

		for (UserDTO userDTO : users) {
			userDTO.setPromo_code_redemptions(getPromoCodeRedemptions(userDTO.getId()));
		}

		return users;
    }
    
    public List<UserDTO> getUserByDOB(String dob, String ownership) {

		List<UserDTO> users = null;

		if (ownership != null) {
			users =  jdbcTemplate.query(
					"select u.id id, u.first_name first_name, u.last_name last_name, u.email email,\r\n"
							+ " u.phone phone, u.address1 address1, u.address2 address2, u.city city, \r\n"
							+ "u.country country, u.county_or_state county_or_state, u.postal_code postal_code, \r\n"
							+ "(u.card_id IS NOT NULL and u.card_id <> '') credit_card_registered, \r\n"
							+ "CASE \r\n"
							+ "	when (select TO_CHAR(u.birth_date,'YYYY-MM-DD')) != '' then (select TO_CHAR(u.birth_date,'YYYY-MM-DD')) \r\n"
							+ "	when (select TO_CHAR(sc.birth_date,'YYYY-MM-DD')) != '' then (select TO_CHAR(sc.birth_date,'YYYY-MM-DD')) \r\n"
							+ "END birth_date, \r\n"
							+ "u.role user_role, \r\n"
							+ "u.ownership ownership \r\n"
							+ "from smart_cards sc right outer join users u on u.id = sc.user_id\r\n"
							+ "and LOWER(sc.first_name) = LOWER(u.first_name) and LOWER(sc.last_name) = LOWER(u.last_name)\r\n"
							+ "where TO_CHAR(u.birth_date,'YYYY-MM-DD') LIKE '%'||?||'%'"
							+ "and u.ownership=?",
					(rs, rowNum) -> new UserDTO(
							rs.getInt("id"),
							rs.getString("first_name"),
							rs.getString("last_name"),
							rs.getString("birth_date"),
							rs.getString("email"),
							rs.getString("phone"),
							rs.getString("address1"),
							rs.getString("address2"),
							rs.getString("city"),
							rs.getString("country"),
							rs.getString("county_or_state"),
							rs.getString("postal_code"),
							null,
							null,
							rs.getBoolean("credit_card_registered"),
							null,
							rs.getString("user_role"),
							rs.getString("ownership")),
					dob, ownership
			);
		} else {
			users =  jdbcTemplate.query(
					"select u.id id, u.first_name first_name, u.last_name last_name, u.email email,\r\n"
							+ " u.phone phone, u.address1 address1, u.address2 address2, u.city city, \r\n"
							+ "u.country country, u.county_or_state county_or_state, u.postal_code postal_code, \r\n"
							+ "(u.card_id IS NOT NULL and u.card_id <> '') credit_card_registered, \r\n"
							+ "CASE \r\n"
							+ "	when (select TO_CHAR(u.birth_date,'YYYY-MM-DD')) != '' then (select TO_CHAR(u.birth_date,'YYYY-MM-DD')) \r\n"
							+ "	when (select TO_CHAR(sc.birth_date,'YYYY-MM-DD')) != '' then (select TO_CHAR(sc.birth_date,'YYYY-MM-DD')) \r\n"
							+ "END birth_date, \r\n"
							+ "u.role user_role, \r\n"
							+ "u.ownership ownership \r\n"
							+ "from smart_cards sc right outer join users u on u.id = sc.user_id\r\n"
							+ "and LOWER(sc.first_name) = LOWER(u.first_name) and LOWER(sc.last_name) = LOWER(u.last_name)\r\n"
							+ "where TO_CHAR(u.birth_date,'YYYY-MM-DD') LIKE '%'||?||'%'",
					(rs, rowNum) -> new UserDTO(
							rs.getInt("id"),
							rs.getString("first_name"),
							rs.getString("last_name"),
							rs.getString("birth_date"),
							rs.getString("email"),
							rs.getString("phone"),
							rs.getString("address1"),
							rs.getString("address2"),
							rs.getString("city"),
							rs.getString("country"),
							rs.getString("county_or_state"),
							rs.getString("postal_code"),
							null,
							null,
							rs.getBoolean("credit_card_registered"),
							null,
							rs.getString("user_role"),
							rs.getString("ownership")),
					dob
			);
		}
    	
    	for (UserDTO userDTO : users) {
    		userDTO.setOwned_cards(getOwnedCards(userDTO.getId()));
		}
    	
    	for (UserDTO userDTO : users) {
    		userDTO.setAssigned_cards(getAssignedCards(userDTO.getId()));
		}

		for (UserDTO userDTO : users) {
			userDTO.setPromo_code_redemptions(getPromoCodeRedemptions(userDTO.getId()));
		}

		return users;
    }
    
    public List<UserDTO> getResortContactPerson(Integer resort_id, String user_type) {

		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();

		args.add(user_type);
		argTypes.add(Types.VARCHAR);

		if (resort_id != -1) {
			args.add(resort_id);
			argTypes.add(Types.INTEGER);
		}

		int[] argTypesInt = argTypes.stream().mapToInt(Integer::intValue).toArray();

		List<UserDTO> users =  jdbcTemplate.query(
    			"select u.id id, u.first_name first_name, u.last_name last_name, u.email email,\r\n"
                		+ " u.phone phone, u.address1 address1, u.address2 address2, u.city city, \r\n"
                        + "u.country country, u.county_or_state county_or_state, u.postal_code postal_code, \r\n"
                        + "(select TO_CHAR(u.birth_date,'YYYY-MM-DD')) birth_date, \r\n"
						+ "u.role user_role, \r\n"
						+ "u.ownership ownership \r\n"
                + "from users u \r\n"
                + " where u.role=?::users_roles \r\n"
				+ (resort_id != -1 ? "and u.resort_id=?" : "")
				,
				args.toArray(), argTypesInt,
                (rs, rowNum) -> new UserDTO(
                		rs.getInt("id"),
                		rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("birth_date"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("address1"),
                        rs.getString("address2"),
                        rs.getString("city"),
                        rs.getString("country"),
                        rs.getString("county_or_state"),
                        rs.getString("postal_code"),
                        null,
                        null,
                        null,
						null,
						rs.getString("user_role"),
						rs.getString("ownership"))
        );
    	
    	return users;
    }
}
