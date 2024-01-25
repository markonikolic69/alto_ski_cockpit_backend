package ski.alto.cockpit.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GateUsageDTO {
    private String usage_date;
    private String lift_number;
    private String lift_name;
    private Integer number_of_usages;
}
