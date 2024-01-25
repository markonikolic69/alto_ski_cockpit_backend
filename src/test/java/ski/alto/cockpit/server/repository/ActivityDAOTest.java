package ski.alto.cockpit.server.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ski.alto.cockpit.server.model.ActivityDTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ActivityDAOTest {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    ActivityDAO activityDAO;

    @Test
    void getDTACardByCardNumberAndResortIdForDateRange() {
        List<ActivityDTO> result = activityDAO.getDTACardByCardNumberAndResortIdForDateRange("01-16147133535030201464-3",
                "2020-01-18 09:36:59.000",
                "2020-01-20 09:36:59.000",
                3099, 5);

        logger.info(result != null ? result.toString() : "No result found");
    }

    @Test
    void getAllDTACardActivitiesForResortIdForToday() {
        List<ActivityDTO> result = activityDAO.getAllDTACardActivitiesForResortIdForToday(
                "2020-01-18 09:36:59.000", 40, "SkiClubBG");
        logger.info(result != null ? result.toString() : "No result found");
    }
}