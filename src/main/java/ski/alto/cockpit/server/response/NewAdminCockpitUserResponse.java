package ski.alto.cockpit.server.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NewAdminCockpitUserResponse {
	private int responseCode;
	private String responseMessage;
}
