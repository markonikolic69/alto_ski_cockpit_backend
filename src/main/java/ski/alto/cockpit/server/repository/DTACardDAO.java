package ski.alto.cockpit.server.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ski.alto.cockpit.server.model.DTACardDTO;

@Component
public class DTACardDAO {
	@Autowired
    JdbcTemplate jdbcTemplate;
	public List<DTACardDTO> getDTACardByCardNumber(String card_number) {

    	List<DTACardDTO> dtaCards =  jdbcTemplate.query(
                "select id, card_number, TO_CHAR(record_expires,'YYYY-MM-DD') record_expires, TO_CHAR(birth_date,'YYYY-MM-DD') birth_date, status, first_name, last_name, image\r\n"
                + "from smart_cards where card_number=?",
                (rs, rowNum) -> new DTACardDTO(
						rs.getInt("id"),
                		rs.getString("card_number"),
						rs.getString("record_expires"),
						rs.getString("birth_date"),
                        rs.getString("status"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("image")),
                card_number
        );
    	
    	return dtaCards;
    }
	
	public List<DTACardDTO> getDTACardByHolderId(int user_holder_id) {

    	List<DTACardDTO> dtaCards =  jdbcTemplate.query(
                "select id, card_number, TO_CHAR(record_expires,'YYYY-MM-DD') record_expires, TO_CHAR(birth_date,'YYYY-MM-DD') birth_date, status, first_name, last_name, image\r\n"
                + "from smart_cards where card_number=?",
                (rs, rowNum) -> new DTACardDTO(
						rs.getInt("id"),
                		rs.getString("card_number"),
						rs.getString("record_expires"),
						rs.getString("birth_date"),
                        rs.getString("status"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("image")),
                user_holder_id
        );
    	
    	return dtaCards;
    }
}
