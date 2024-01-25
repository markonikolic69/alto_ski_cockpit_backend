package ski.alto.cockpit.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ski.alto.cockpit.server.model.SmartCardUsages;
import ski.alto.cockpit.server.model.UserReportDTO;
import ski.alto.cockpit.server.repository.SmartCardUsagesDAO;
import ski.alto.cockpit.server.repository.SmartCardUsagesRepository;
import ski.alto.cockpit.server.repository.UserReportDAO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SmartCardUsagesService {

    @Autowired
    SmartCardUsagesDAO smartCardUsagesDAO;

    public List<SmartCardUsages> getSmartCardUsages(String ownership) {
        return smartCardUsagesDAO.findTop100ByOrderByStartDateDesc(ownership);
    }

    public List<SmartCardUsages> getSmartCardUsages(Integer resortId, String ownership) {
        return smartCardUsagesDAO.findTop100ByResortIdOrderByStartDateDesc(resortId, ownership);
    }

    public List<SmartCardUsages> getSmartCardUsages(LocalDateTime startDate, LocalDateTime endDate, String ownership) {
        return smartCardUsagesDAO.findByStartDateBetweenOrderByStartDateDesc(startDate, endDate, ownership);
    }

    public List<SmartCardUsages> getSmartCardUsages(Integer resortId, LocalDateTime startDate, LocalDateTime endDate, String ownership) {
        return smartCardUsagesDAO.findByResortIdAndStartDateBetweenOrderByStartDateDesc(resortId, startDate, endDate, ownership);
    }
}
