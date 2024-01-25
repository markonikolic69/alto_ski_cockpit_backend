package ski.alto.cockpit.server.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ski.alto.cockpit.server.model.CockpitResortReports;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class CockpitResortReportsRepositoryTest {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    CockpitResortReportsRepository cockpitResortReportsRepository;

    @Test
    void findByResortIdAndTransactionDateBetween() {
        List<CockpitResortReports> result = cockpitResortReportsRepository.findByResortIdAndTransactionDateBetween(
                3099,
                LocalDateTime.parse("2020-01-18T09:36:59.000"),
                LocalDateTime.parse("2020-01-20T09:36:59.000")
        );
        logger.info(result != null ? result.toString() : "No result found");
        assert(result != null);
    }
}