package ski.alto.cockpit.server.controller.request;

import lombok.Data;

@Data
public class FailedTransactionsRequest {

    private Integer lastKnownFailedChargeId;
    private String ownership;
}
