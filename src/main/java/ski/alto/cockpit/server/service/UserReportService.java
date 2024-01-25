package ski.alto.cockpit.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ski.alto.cockpit.server.model.UserReportDTO;
import ski.alto.cockpit.server.repository.UserReportDAO;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserReportService {

    @Autowired
    UserReportDAO userReportDAO;

    public List<UserReportDTO> getUserReport(Integer resortId, LocalDate startDate, LocalDate endDate, String ownership) {

        return userReportDAO.getUserReport(resortId, startDate, endDate, ownership);
    }
}
