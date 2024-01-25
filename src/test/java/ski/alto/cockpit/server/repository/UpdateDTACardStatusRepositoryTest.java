package ski.alto.cockpit.server.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class UpdateDTACaresdStatusRepositoryTest {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    UpdateDTACardStatusRepository updateDTACardStatusRepository;

    @Test
    void blockCardWithCardNumber() {
        Integer result = updateDTACardStatusRepository.blockCardWithCardNumber("active", "01-16147133535016079240-5");
        logger.info(result != null ? result.toString() : "No result found");
        assert (result != null);
    }
}