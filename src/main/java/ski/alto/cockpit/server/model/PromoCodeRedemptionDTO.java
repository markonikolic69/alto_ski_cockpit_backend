package ski.alto.cockpit.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PromoCodeRedemptionDTO {
    private String code;
    private String redemptionTime;
}
