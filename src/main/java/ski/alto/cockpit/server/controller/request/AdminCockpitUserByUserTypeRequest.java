package ski.alto.cockpit.server.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@NoArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
public class AdminCockpitUserByUserTypeRequest {
    private String user_type;
    private String ownership;
}
