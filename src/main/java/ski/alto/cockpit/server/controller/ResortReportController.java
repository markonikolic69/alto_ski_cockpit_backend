package ski.alto.cockpit.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ski.alto.cockpit.server.controller.request.ParseResortReportRequest;
import ski.alto.cockpit.server.controller.request.ResortReportRequest;
import ski.alto.cockpit.server.model.ResortReportDTO;
import ski.alto.cockpit.server.model.UserReportDTO;
import ski.alto.cockpit.server.service.AmazonS3Service;
import ski.alto.cockpit.server.service.ResortReportParserService;
import ski.alto.cockpit.server.service.UsersService;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

@RestController
@CrossOrigin
public class ResortReportController {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    UsersService usersService;

    @Autowired
    ResortReportParserService resortReportParserService;

    @Autowired
    AmazonS3Service amazonS3Service;

    private final int NUMBER_OF_MONTHS_TO_DISPLAY = 12;

    @PostMapping("/resort-report")
    @CrossOrigin(origins = {"http://94.127.4.240:4200", "http://localhost:8081", "https://cockpit.alto.ski", "http://65.21.206.110:8081", "https://skiclubgb-cockpit-client-ec942f8fa647.herokuapp.com", "https://skiclub.alto.ski"}, methods = {RequestMethod.POST, RequestMethod.OPTIONS})
    public List<ResortReportDTO> getResortReport(@RequestBody ResortReportRequest request, @RequestParam String ownership) {

        return resortReportParserService.getResortReportList(request.getResortName(), NUMBER_OF_MONTHS_TO_DISPLAY, ownership);
    }

    @PostMapping("/resort-report-details")
    @CrossOrigin(origins = {"http://94.127.4.240:4200", "http://localhost:8081", "https://cockpit.alto.ski", "http://65.21.206.110:8081", "https://skiclubgb-cockpit-client-ec942f8fa647.herokuapp.com", "https://skiclub.alto.ski"}, methods = {RequestMethod.POST, RequestMethod.OPTIONS})
    public List<ResortReportDTO> getResortReportDetails(@RequestBody ResortReportRequest request, @RequestParam String ownership) {

        return resortReportParserService.getResortReportList(request.getResortName(), NUMBER_OF_MONTHS_TO_DISPLAY, ownership);
    }

    @PostMapping("/parse-resort-reports")
    public void parseResortReports(@RequestBody ParseResortReportRequest request, @RequestParam String ownership) {
        resortReportParserService.parseS3ReportsByResortName(request.getResortName(), request.getStartDate(), ownership);
        logger.info("Finisher reports parsing for resort " + request.getResortName());
    }

    @PostMapping("/parse-all-resort-reports")
    @CrossOrigin(origins = {"http://94.127.4.240:4200", "http://localhost:8081", "https://cockpit.alto.ski", "http://65.21.206.110:8081", "https://skiclubgb-cockpit-client-ec942f8fa647.herokuapp.com", "https://skiclub.alto.ski"}, methods = {RequestMethod.POST, RequestMethod.OPTIONS})
    public void parseAllResortReports(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, @RequestParam String ownership) {
        if(startDate == null) {
        	startDate = LocalDate.now();
        	startDate.minusDays(1);
        }
    	resortReportParserService.parseS3ReportsAllResorts(startDate, ownership);
        logger.info("Finisher reports parsing for all resort");
    }



}
