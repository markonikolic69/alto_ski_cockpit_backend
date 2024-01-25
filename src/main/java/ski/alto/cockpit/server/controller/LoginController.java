package ski.alto.cockpit.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;
import ski.alto.cockpit.server.controller.request.LoginRequest;
import ski.alto.cockpit.server.model.AdminCockpitUsersDTO;
import ski.alto.cockpit.server.model.AdminUsersPreviewDTO;
import ski.alto.cockpit.server.model.UserDTO;
import ski.alto.cockpit.server.repository.AdminUsersPreviewDAO;
import ski.alto.cockpit.server.repository.UserDAO;
import ski.alto.cockpit.server.response.LoginResponse;
import ski.alto.cockpit.server.response.ResponseCodes;
import ski.alto.cockpit.server.service.AdminCockpitUsersService;
import ski.alto.cockpit.server.utility.JwtTokenBuilder;
import ski.alto.cockpit.server.utility.OwnershipUtil;

import java.util.List;

@RestController
public class LoginController {

    @Autowired
    AdminCockpitUsersService adminCockpitUsersService;
    @Autowired
    AdminUsersPreviewDAO adminUsersPreviewDAO;

    @Autowired
    UserDAO userDAO;

//    @CrossOrigin(origins = "http://94.127.4.240:4200")	//	SERVER CONFIG
    @CrossOrigin(origins = {"http://94.127.4.240:4200", "http://localhost:8081", "https://cockpit.alto.ski", "http://65.21.206.110:8081"})
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        AdminCockpitUsersDTO u = adminCockpitUsersService.findByEmail(request.getEmail());
        AdminUsersPreviewDTO user = null;
        Integer response_code = ResponseCodes.RESPONSE_CODE_OK.getValue();
        String response_message = "OK";
        String token = null;

        if (u != null) {
        	if(BCrypt.checkpw(request.getPassword(), u.getPasswordDigest())) {
        		user = adminUsersPreviewDAO.getAdminCockpitUserByID(u.getId(), OwnershipUtil.parseOwnership(request.getOwnership()));
                if (user == null) {
                    response_code = ResponseCodes.RESPONSE_CODE_WRONG_EMAIL.getValue();
                    response_message = "You've entered a wrong email. Please check your spelling and try again.";
                }
        	}
        	else {
            	response_code = ResponseCodes.RESPONSE_CODE_WRONG_PASSWORD.getValue();
            	response_message = "You've entered a wrong password. Please check your spelling and try again.";
            }
        }
        else {
        	response_code = ResponseCodes.RESPONSE_CODE_WRONG_EMAIL.getValue();
            response_message = "You've entered a wrong email. Please check your spelling and try again.";
        }

        if (response_code == ResponseCodes.RESPONSE_CODE_OK.getValue()) {
            List<UserDTO> coreUser = userDAO.getUserByEmail(request.getEmail(),
                    OwnershipUtil.parseOwnership(OwnershipUtil.SKICLUB_GB));
            if (coreUser != null && coreUser.size() > 0) {
                token = new JwtTokenBuilder().build(coreUser.get(0));
            }
        }

        LoginResponse response = new LoginResponse(user, response_code, response_message, token);

        return response;
    }
}
