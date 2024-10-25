package ski.alto.cockpit.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ski.alto.cockpit.server.controller.request.TransactionReportRequest;
import ski.alto.cockpit.server.controller.request.UserReportRequest;
import ski.alto.cockpit.server.model.SmartCardUsages;
import ski.alto.cockpit.server.model.UserReportDTO;
import ski.alto.cockpit.server.service.SmartCardUsagesService;
import ski.alto.cockpit.server.service.UserReportService;
import ski.alto.cockpit.server.utility.OwnershipUtil;

import java.util.List;
import java.util.Map;

@RestController
public class TransactionReportController {
	Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    @Autowired
    SmartCardUsagesService smartCardUsagesService;

    @PostMapping("/transaction-report")
    @CrossOrigin(origins = {"http://94.127.4.240:4200", "http://reciklomatmobapp.suprabit.rs:8081/", "http://localhost:4200", "http://localhost:8081", "https://cockpit.alto.ski", "http://65.21.206.110:8081", "https://skiclubgb-cockpit-client-ec942f8fa647.herokuapp.com", "https://skiclub.alto.ski", "https://alto-ski-cockpit-client-393cf5cd662a.herokuapp.com"})
    public List<SmartCardUsages> smartCardUsagesService(@RequestBody TransactionReportRequest request, @RequestParam String ownership) {
    	logger.info("ownership_request = " + ownership);
        String ownership_parsed = OwnershipUtil.parseOwnership(ownership);//OwnershipUtil.parseOwnership(request.getOwnership());
        logger.info("ownership_parsed = " + ownership_parsed);

        
        if ((request.getResortId() == null || request.getResortId() == -1) && request.getStartDate() == null) {
            return smartCardUsagesService.getSmartCardUsages(ownership_parsed);
        } else if (request.getResortId() != null && request.getResortId() != -1 && request.getStartDate() == null) {
            return smartCardUsagesService.getSmartCardUsages(request.getResortId(), ownership_parsed);
        } else if (request.getResortId() == null || request.getResortId() == -1) {
            return smartCardUsagesService.getSmartCardUsages(request.getStartDate(), request.getEndDate(), ownership_parsed);
        } else {
            return smartCardUsagesService.getSmartCardUsages(request.getResortId(), request.getStartDate(), request.getEndDate(), ownership_parsed);
        }
    }
}
