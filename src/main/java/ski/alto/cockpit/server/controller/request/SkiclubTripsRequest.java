package ski.alto.cockpit.server.controller.request;



import lombok.Data;

@Data
public class SkiclubTripsRequest {
	
    private Integer resortId;
    private String  year;
    private String month;


}
