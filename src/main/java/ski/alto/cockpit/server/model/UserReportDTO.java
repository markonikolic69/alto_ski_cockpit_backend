package ski.alto.cockpit.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserReportDTO {

    private String resortName;
    private Double chargedSum;
    private String chargedCurrency;
    private Integer totalTransactions;
    private String chargedStatus;

}
