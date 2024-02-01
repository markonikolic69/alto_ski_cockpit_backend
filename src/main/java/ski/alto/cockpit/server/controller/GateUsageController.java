package ski.alto.cockpit.server.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ski.alto.cockpit.server.model.ActivityDTO;
import ski.alto.cockpit.server.model.GateUsageDTO;
import ski.alto.cockpit.server.repository.ActivityDAO;
import ski.alto.cockpit.server.repository.GateUsageDAO;
import ski.alto.cockpit.server.utility.OwnershipUtil;

@RestController
public class GateUsageController {
	Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	@Autowired
    GateUsageDAO gateUsageDAO;

//    @CrossOrigin(origins = "http://94.127.4.240:4200")	//	SERVER CONFIG
    @CrossOrigin(origins = {"http://94.127.4.240:4200", "http://localhost:8081", "https://cockpit.alto.ski", "http://65.21.206.110:8081", "https://skiclubgb-cockpit-client-ec942f8fa647.herokuapp.com"})
    @GetMapping("/gate_usages")
    public List<GateUsageDTO> getGateUsages(@RequestParam Map<String,String> requestParams) {
    	
    	logger.info("getGateUsages ENTER");
    	
    	String start_date = requestParams.get("start_date");
    	logger.info(start_date);
    	String end_date = requestParams.get("end_date");
    	logger.info(end_date);
    	Integer resort_id = Integer.parseInt(requestParams.get("resort_id"));
    	logger.info(String.valueOf(resort_id));
		String ownership = OwnershipUtil.parseOwnership(requestParams.get("ownership"));
		logger.info(String.valueOf(ownership));
    	
    	return gateUsageDAO.getGateUsagesByCardNumberAndResortIdForDateRange(start_date, end_date, resort_id, ownership);
    }
}
