package ski.alto.cockpit.server.controller.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ResortReportRequest {

    private String resortName;
    private String username;

}
