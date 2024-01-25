package ski.alto.cockpit.server.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ski.alto.cockpit.server.model.UserReportDTO;
import ski.alto.cockpit.server.utility.OwnershipUtil;

import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserReportDAOTest {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    UserReportDAO userReportDAO;

    @Test
    public void getUserReport() {

        LocalDate startDate = LocalDate.of(2010, Month.JANUARY, 1);
        LocalDate endDate = LocalDate.of(2022, Month.AUGUST, 1);

        List<UserReportDTO> result = userReportDAO.getUserReport(null, startDate, endDate,
                OwnershipUtil.parseOwnership(OwnershipUtil.SKICLUB_GB));
        result.forEach(report -> logger.info(report.toString()));

    }

}