package ski.alto.cockpit.server.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ski.alto.cockpit.server.model.SmartCardUsages;

import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class SmartCardUsagesDAOTest {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    SmartCardUsagesDAO smartCardUsagesDAO;

    @Test
    void findTop100ByOrderByStartDateDesc() {
        List<SmartCardUsages> result = smartCardUsagesDAO.findTop100ByOrderByStartDateDesc("SkiClubGB");
        logger.info(result != null ? "" + result.size() : "No result found");

        result = smartCardUsagesDAO.findTop100ByOrderByStartDateDesc(null);
        logger.info(result != null ? "" + result.size() : "No result found");
    }

    @Test
    void findTop100ByResortIdOrderByStartDateDesc() {
        List<SmartCardUsages> result = smartCardUsagesDAO.findTop100ByResortIdOrderByStartDateDesc(110, "SkiClubGB");
        logger.info(result != null ? "" + result.size() : "No result found");

        result = smartCardUsagesDAO.findTop100ByResortIdOrderByStartDateDesc(110, null);
        logger.info(result != null ? "" + result.size() : "No result found");
    }

    @Test
    void findByStartDateBetweenOrderByStartDateDesc() {
         List<SmartCardUsages> result = smartCardUsagesDAO.findByStartDateBetweenOrderByStartDateDesc(
                LocalDateTime.parse("2023-01-01T00:00:00"),
                LocalDateTime.parse("2023-02-01T00:00:00"), "SkiClubGB");
        logger.info(result != null ? "" + result.size() : "No result found");

        result = smartCardUsagesDAO.findByStartDateBetweenOrderByStartDateDesc(
                LocalDateTime.parse("2023-01-01T00:00:00"),
                LocalDateTime.parse("2023-02-01T00:00:00"), null);
        logger.info(result != null ? "" + result.size() : "No result found");
    }

    @Test
    void findByResortIdAndStartDateBetweenOrderByStartDateDesc() {
        List<SmartCardUsages> result = smartCardUsagesDAO.findByResortIdAndStartDateBetweenOrderByStartDateDesc(
                110,
                LocalDateTime.parse("2023-01-01T00:00:00"),
                LocalDateTime.parse("2023-02-01T00:00:00"), "SkiClubGB");
        logger.info(result != null ? "" + result.size() : "No result found");

        result = smartCardUsagesDAO.findByResortIdAndStartDateBetweenOrderByStartDateDesc(
                110,
                LocalDateTime.parse("2023-01-01T00:00:00"),
                LocalDateTime.parse("2023-02-01T00:00:00"), null);
        logger.info(result != null ? "" + result.size() : "No result found");
    }
}