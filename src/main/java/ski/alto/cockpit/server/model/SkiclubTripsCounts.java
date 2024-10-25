package ski.alto.cockpit.server.model;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkiclubTripsCounts {
	private String month;
	private String month_string;
	private String year;
	private Integer count;
	private Integer resort_id;
	private Integer confirmed;
	
	
	
	
	@Override
	public String toString() {
		return "SkiclubTripsCounts [month=" + month + ", month_string=" + month_string + ", year=" + year + ", count="
				+ count + ", resort_id=" + resort_id + ", confirmed=" + confirmed + "]";
	}
	
	
	
}
