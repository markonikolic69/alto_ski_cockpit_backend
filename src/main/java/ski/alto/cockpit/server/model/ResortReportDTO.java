package ski.alto.cockpit.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResortReportDTO {

    private String reportName;
    private String reportUrl;
    private String invoiceUrl;
    private Integer totalSoldProducts;
    private Double totalIncomeGross;
    private Double totalIncomeNet;

}
