package ski.alto.cockpit.server.controller.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserReportRequest {

    private Integer resortId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String ownership;
    
}
