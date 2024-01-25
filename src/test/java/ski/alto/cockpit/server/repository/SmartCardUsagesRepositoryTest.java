package ski.alto.cockpit.server.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ski.alto.cockpit.server.model.GateUsageDTO;
import ski.alto.cockpit.server.model.SmartCardUsages;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class SmartCardUsagesRepositoryTest {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    SmartCardUsagesRepository smartCardUsagesRepository;

    @Test
    void findTop100ByOrderByStartDateDesc() {
        List<SmartCardUsages> result = smartCardUsagesRepository.findTop100ByOrderByStartDateDesc();
        logger.info(result != null ? result.toString() : "No result found");
        assert (result != null);
    }

    @Test
    void findTop100ByResortIdOrderByStartDateDesc() {
        List<SmartCardUsages> result = smartCardUsagesRepository.findTop100ByResortIdOrderByStartDateDesc(1);
        logger.info(result != null ? result.toString() : "No result found");
        assert (result != null);
    }

    @Test
    void findByStartDateBetweenOrderByStartDateDesc() {
        List<SmartCardUsages> result = smartCardUsagesRepository.findByStartDateBetweenOrderByStartDateDesc(
                LocalDateTime.parse("2020-01-18T09:36:59.000"),
                LocalDateTime.parse("2020-01-20T09:36:59.000")
        );
        logger.info(result != null ? result.toString() : "No result found");
        assert (result != null);
    }

    @Test
    void findByResortIdAndStartDateBetweenOrderByStartDateDesc() {
        List<SmartCardUsages> result = smartCardUsagesRepository.findByResortIdAndStartDateBetweenOrderByStartDateDesc(
                1,
                LocalDateTime.parse("2020-01-18T09:36:59.000"),
                LocalDateTime.parse("2020-01-20T09:36:59.000")
        );
        logger.info(result != null ? result.toString() : "No result found");
        assert (result != null);
    }
}