package ski.alto.cockpit.server.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ski.alto.cockpit.server.model.Resorts;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ResortsRepositoryTest {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    ResortsRepository resortsRepository;

    @Test
    void findByName() {
        List<Resorts> result = resortsRepository.findByName("Alto.Ski");
        logger.info(result != null ? result.toString() : "No result found");
        assert (result != null);
    }

    @Test
    void findAllByOrderByIdAsc() {
        List<Resorts> result = resortsRepository.findAllByOrderByIdAsc();
        logger.info(result != null ? result.toString() : "No result found");
        assert (result != null);
    }
}