package ski.alto.cockpit.server.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ski.alto.cockpit.server.controller.request.AdminCockpitUserByUserTypeRequest;
import ski.alto.cockpit.server.controller.request.NewAdminCockpitUserRequest;
import ski.alto.cockpit.server.model.AdminCockpitUsersDTO;
import ski.alto.cockpit.server.model.AdminUsersPreviewDTO;
import ski.alto.cockpit.server.repository.AdminUsersPreviewDAO;
import ski.alto.cockpit.server.response.NewAdminCockpitUserResponse;
import ski.alto.cockpit.server.utility.OwnershipUtil;

@RestController
public class AdminUsersPreviewController {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    @Autowired
    AdminUsersPreviewDAO dtaAdminUsersPreviewDAO;
    @CrossOrigin(origins = {"http://94.127.4.240:4200", "http://localhost:8081", "https://cockpit.alto.ski", "http://65.21.206.110:8081", "https://skiclubgb-cockpit-client-ec942f8fa647.herokuapp.com", "https://skiclub.alto.ski"})
    @GetMapping("/admin_cockpit_users")
    public List<AdminUsersPreviewDTO> getUsers(@RequestBody AdminCockpitUserByUserTypeRequest request) {
    	return dtaAdminUsersPreviewDAO.getAdminCockpitUserByUserType(request);
    }
    
    @CrossOrigin(origins = {"http://94.127.4.240:4200", "http://localhost:8081", "https://cockpit.alto.ski", "http://65.21.206.110:8081", "https://skiclubgb-cockpit-client-ec942f8fa647.herokuapp.com", "https://skiclub.alto.ski"})
    @PostMapping("/admin_cockpit_users")
    public NewAdminCockpitUserResponse addUser(@RequestBody NewAdminCockpitUserRequest request) {
    	return dtaAdminUsersPreviewDAO.addAdminCockpitUser(request);
    }
    
    @CrossOrigin(origins = {"http://94.127.4.240:4200", "http://localhost:8081", "https://cockpit.alto.ski", "http://65.21.206.110:8081", "https://skiclubgb-cockpit-client-ec942f8fa647.herokuapp.com", "https://skiclub.alto.ski"})
    @PostMapping("/admin_cockpit_users_edit_user")
    public Integer editUser(@RequestBody NewAdminCockpitUserRequest request) {
    	return dtaAdminUsersPreviewDAO.editAdminCockpitUser(request);
    }
    
    @CrossOrigin(origins = {"http://94.127.4.240:4200", "http://localhost:8081", "https://cockpit.alto.ski", "http://65.21.206.110:8081", "https://skiclubgb-cockpit-client-ec942f8fa647.herokuapp.com", "https://skiclub.alto.ski"})
    @PostMapping("/admin_cockpit_users_remove_user")
    public Integer removeUser(@RequestBody NewAdminCockpitUserRequest request) {
    	return dtaAdminUsersPreviewDAO.removeAdminCockpitUser(request);
    }
    
    @CrossOrigin(origins = {"http://94.127.4.240:4200", "http://localhost:8081", "https://cockpit.alto.ski", "http://65.21.206.110:8081", "https://skiclubgb-cockpit-client-ec942f8fa647.herokuapp.com", "https://skiclub.alto.ski"})
    @GetMapping("/admin_cockpit_users_search")
    public List<AdminUsersPreviewDTO> searchUsers(@RequestParam Map<String,String> requestParams) {
    	String search_input = requestParams.get("search_input");
    	boolean search_by_first_name = Boolean.parseBoolean(requestParams.get("search_by_first_name"));
		boolean search_by_last_name = Boolean.parseBoolean(requestParams.get("search_by_last_name"));
		boolean search_by_email = Boolean.parseBoolean(requestParams.get("search_by_email"));
		boolean search_by_id = Boolean.parseBoolean(requestParams.get("search_by_id"));

		String ownership = OwnershipUtil.parseOwnership(requestParams.get("ownership"));


		logger.info(search_input);
		logger.info(String.valueOf(search_by_first_name));
		logger.info(String.valueOf(search_by_last_name));
		logger.info(String.valueOf(search_by_email));
		logger.info(String.valueOf(search_by_id));
		logger.info(String.valueOf(ownership));

        List<AdminUsersPreviewDTO> users = new ArrayList<>();
        if(search_by_first_name) {
        	List<AdminUsersPreviewDTO> fn_users = dtaAdminUsersPreviewDAO.searchAdminCockpitUsersByFirstName(search_input, ownership);
        	for (AdminUsersPreviewDTO adminCockpitUsersDTO : fn_users) {
        		if(!users.stream().filter(o -> o.getId() == adminCockpitUsersDTO.getId()).findFirst().isPresent())
        			users.add(adminCockpitUsersDTO);
			}
        }
        if(search_by_last_name) {
        	List<AdminUsersPreviewDTO> fn_users = dtaAdminUsersPreviewDAO.searchAdminCockpitUsersByLastName(search_input, ownership);
        	for (AdminUsersPreviewDTO adminCockpitUsersDTO : fn_users) {
        		if(!users.stream().filter(o -> o.getId() == adminCockpitUsersDTO.getId()).findFirst().isPresent())
        			users.add(adminCockpitUsersDTO);
			}
        
        }
        if(search_by_email) {
        	List<AdminUsersPreviewDTO> fn_users = dtaAdminUsersPreviewDAO.searchAdminCockpitUsersByEmail(search_input, ownership);
        	for (AdminUsersPreviewDTO adminCockpitUsersDTO : fn_users) {
        		if(!users.stream().filter(o -> o.getId() == adminCockpitUsersDTO.getId()).findFirst().isPresent())
        			users.add(adminCockpitUsersDTO);
			}
        }
        if(search_by_id) {
        	List<AdminUsersPreviewDTO> fn_users = dtaAdminUsersPreviewDAO.searchAdminCockpitUsersByID(search_input, ownership);
        	for (AdminUsersPreviewDTO adminCockpitUsersDTO : fn_users) {
        		if(!users.stream().filter(o -> o.getId() == adminCockpitUsersDTO.getId()).findFirst().isPresent())
        			users.add(adminCockpitUsersDTO);
			}
        }

        return users;
    }
    
}
