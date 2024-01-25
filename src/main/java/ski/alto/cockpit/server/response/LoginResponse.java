package ski.alto.cockpit.server.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ski.alto.cockpit.server.model.AdminUsersPreviewDTO;

@AllArgsConstructor
@Setter
@Getter
public class LoginResponse {
	AdminUsersPreviewDTO user;
	Integer response_code;
	String response_message;
	String token;
}
