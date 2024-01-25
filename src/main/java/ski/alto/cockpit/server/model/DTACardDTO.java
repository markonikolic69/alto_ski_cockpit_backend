package ski.alto.cockpit.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DTACardDTO {
    private Integer id;
    private String card_number;
    private String record_expires;
    private String birth_date;
    private String status;
    private String first_name;
    private String last_name;
    private String image;
}
