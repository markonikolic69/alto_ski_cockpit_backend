package ski.alto.cockpit.server.controller.request;

import javax.persistence.Column;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@NoArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
public class NewAdminCockpitUserRequest {
    private Integer id;
    private String email;
    private String first_name;
    private String last_name;
    private String passwordDigest;
    private String userType;
    private Integer users_id;
    private String resort_name;
}
