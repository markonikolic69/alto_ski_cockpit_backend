package ski.alto.cockpit.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ActivityDTO {
    private String resort_name;
    private String card_number;
    private String activity_date;
    private String reader_name;
    private String block_number;
    private String lift_number;
    private String lift_name;
    private String reader_number;
    private String first_name;
    private String last_name;
    private Integer user_id;
}
