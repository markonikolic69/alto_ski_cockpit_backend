package ski.alto.cockpit.server.controller.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ParseResortReportRequest {

    private String resortName;
    private LocalDate startDate;

}
