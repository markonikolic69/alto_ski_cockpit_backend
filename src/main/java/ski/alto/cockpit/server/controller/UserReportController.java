package ski.alto.cockpit.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ski.alto.cockpit.server.controller.request.UserReportRequest;
import ski.alto.cockpit.server.model.UserReportDTO;
import ski.alto.cockpit.server.service.UserReportService;
import ski.alto.cockpit.server.utility.OwnershipUtil;

import java.util.List;

@RestController
public class UserReportController {

    @Autowired
    UserReportService userReportService;

    @PostMapping("/user-report")
    @CrossOrigin(origins = {"http://94.127.4.240:4200", "http://localhost:8081", "https://cockpit.alto.ski", "http://65.21.206.110:8081", "https://skiclubgb-cockpit-client-ec942f8fa647.herokuapp.com", "https://skiclub.alto.ski"})
    public List<UserReportDTO> getUserReport(@RequestBody UserReportRequest request) {

        return userReportService.getUserReport(request.getResortId(),
                request.getStartDate(), request.getEndDate(), OwnershipUtil.parseOwnership(request.getOwnership()));
    }
}
