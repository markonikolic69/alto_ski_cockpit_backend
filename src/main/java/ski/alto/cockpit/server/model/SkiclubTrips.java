package ski.alto.cockpit.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkiclubTrips {
    private String date_from;
    private String date_to;
    private boolean confirmed;
    private String first_name;
    private String last_name;
    private Integer user_id;
    private Integer resort_id;
    private String dob;
    private String email;
	@Override
	public String toString() {
		return "SkiclubTrips [date_from=" + date_from + ", date_to=" + date_to + ", confirmed=" + confirmed
				+ ", first_name=" + first_name + ", last_name=" + last_name + ", user_id=" + user_id + ", resort_id="
				+ resort_id + ", dob=" + dob + ", email=" + email + "]";
	}
    
    
    
}
