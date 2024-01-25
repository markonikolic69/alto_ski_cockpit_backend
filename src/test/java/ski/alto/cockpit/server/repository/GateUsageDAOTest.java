package ski.alto.cockpit.server.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ski.alto.cockpit.server.model.GateUsageDTO;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class GateUsageDAOTest {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    GateUsageDAO gateUsageDAO;

    @Test
    void getGateUsagesByCardNumberAndResortIdForDateRange() {
        List<GateUsageDTO> result = gateUsageDAO.getGateUsagesByCardNumberAndResortIdForDateRange(
                "2020-02-18T12:01:48.000",
                "2020-02-20T12:01:48.000",
                4, "SkiClubGB");
        logger.info(result != null ? "" + result.size() : "No result found");
        assert (result != null);
    }
}