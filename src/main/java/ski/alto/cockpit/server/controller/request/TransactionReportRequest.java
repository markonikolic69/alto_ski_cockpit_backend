package ski.alto.cockpit.server.controller.request;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TransactionReportRequest {

    private Integer resortId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String ownership;
    
}
