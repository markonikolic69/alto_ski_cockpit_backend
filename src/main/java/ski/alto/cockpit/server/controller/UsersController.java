package ski.alto.cockpit.server.controller;


import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ski.alto.cockpit.server.model.UserDTO;
import ski.alto.cockpit.server.repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ski.alto.cockpit.server.model.Users;
import ski.alto.cockpit.server.service.UsersService;
import ski.alto.cockpit.server.utility.OwnershipUtil;

import java.util.List;


@RestController
public class UsersController {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    @Autowired
    UserDAO userDAO;


    @CrossOrigin(origins = {"http://94.127.4.240:4200", "http://reciklomatmobapp.suprabit.rs:8081/", "http://localhost:4200", "http://localhost:8081", "https://cockpit.alto.ski", "http://65.21.206.110:8081", "https://skiclubgb-cockpit-client-ec942f8fa647.herokuapp.com", "https://skiclub.alto.ski", "https://alto-ski-cockpit-client-393cf5cd662a.herokuapp.com"})
    @GetMapping("/users")
    public List<UserDTO> getUsers(@RequestParam Map<String,String> requestParams) {

    	
    	String search_by_dto_str = requestParams.get("search_by_dto");
    	logger.info("search_by_dto_str = " + search_by_dto_str);
    	boolean search_by_dto = false;
    	
        boolean result = false;
        List<UserDTO> users = new ArrayList<>();
        Set<UserDTO> usersSet = new LinkedHashSet<>();
        String search_input = requestParams.get("search_input");
        String ownership = OwnershipUtil.parseOwnership(requestParams.get("ownership"));
    	if(search_by_dto_str != null) {
    		search_by_dto = Boolean.parseBoolean(search_by_dto_str);
    		logger.info("search_by_dto = " + String.valueOf(search_by_dto));
    	}
    	
    	if(search_by_dto) {
    		usersSet.addAll(userDAO.getUserByDTO(search_input, ownership));
    	}else {
        	
        	boolean search_by_first_name = Boolean.parseBoolean(requestParams.get("search_by_first_name"));
    		boolean search_by_last_name = Boolean.parseBoolean(requestParams.get("search_by_last_name"));
    		boolean search_by_email = Boolean.parseBoolean(requestParams.get("search_by_email"));
    		boolean search_by_dob = Boolean.parseBoolean(requestParams.get("search_by_dob"));
    		
    		
    		logger.info(search_input);
    		logger.info(String.valueOf(search_by_first_name));
    		logger.info(String.valueOf(search_by_last_name));
    		logger.info(String.valueOf(search_by_email));
            logger.info(String.valueOf(search_by_dob));
            logger.info(String.valueOf(ownership));

    		

            if(search_by_first_name)
                usersSet.addAll(userDAO.getUserByFirstName(search_input, ownership));
            if(search_by_last_name)
                usersSet.addAll(userDAO.getUserByLastName(search_input, ownership));
            if(search_by_email)
                usersSet.addAll(userDAO.getUserByEmail(search_input, ownership));
            if(search_by_dob)
                usersSet.addAll(userDAO.getUserByDOB(search_input, ownership));
    	}


        users.addAll(usersSet);
        return users;
    }
    @Autowired
    UsersService usersService;

    @CrossOrigin(origins = {"http://94.127.4.240:4200", "http://reciklomatmobapp.suprabit.rs:8081/", "http://localhost:4200", "http://localhost:8081", "https://cockpit.alto.ski", "http://65.21.206.110:8081", "https://skiclubgb-cockpit-client-ec942f8fa647.herokuapp.com", "https://alto-ski-cockpit-client-393cf5cd662a.herokuapp.com"})
    @GetMapping("/resort_contact")
    public List<UserDTO> getResortContactPerson(@RequestParam Map<String,String> requestParams) {

    	Integer resort_id = Integer.parseInt(requestParams.get("resort_id"));
    	String user_type = requestParams.get("user_type");
		
		
		logger.info(resort_id.toString());
		logger.info(user_type);
		
		
        boolean result = false;
        List<UserDTO> users = new ArrayList<>();
        users.addAll(userDAO.getResortContactPerson(resort_id, user_type));

        return users;
    }
    
    @PostMapping("/users")
    @CrossOrigin(origins = {"http://94.127.4.240:4200", "http://reciklomatmobapp.suprabit.rs:8081/", "http://localhost:4200", "http://localhost:8081", "https://cockpit.alto.ski", "http://65.21.206.110:8081", "https://skiclubgb-cockpit-client-ec942f8fa647.herokuapp.com", "https://alto-ski-cockpit-client-393cf5cd662a.herokuapp.com"})
    public List<Users> getAllUsers() {
        return usersService.getAllUsers();
    }
}
