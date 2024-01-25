package ski.alto.cockpit.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class CockpitResortReportDetailsDTO {

    private String CardNumber;
    private String StripeTransactionReference;
    private LocalDate TransactionDate;
    private LocalTime FirstEntryTime;
    private LocalTime LastEntryTime;
    private String DTAOrderNumber;
    private String DTAConfirmationNumber;
    private String ProductName;
    private String ProductId;
    private String Category;
    private String CategoryId;
    private Double TransactionAmountGross;
    private Double CreditCardProcessingFees;
    private Double AltoSkiProcessingFees;
    private Double TotalFees;
    private Double TransactionAmountNet;
    private Integer ChargeItemId;
    private Integer ChargeId;
    private Integer VisitId;
    private Integer ReportId;
}
