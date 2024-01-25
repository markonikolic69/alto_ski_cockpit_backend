package ski.alto.cockpit.server.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ski.alto.cockpit.server.model.DTACardDTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class DTACardDAOTest {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    DTACardDAO dtaCardDAO;

    @Test
    void getDTACardByCardNumber() {
        List<DTACardDTO> result = dtaCardDAO.getDTACardByCardNumber("01-16147133535078065294-8");
        logger.info(result != null ? result.toString() : "No result found");
        assert(result != null);
    }

    @Test
    void getDTACardByHolderId() {
        List<DTACardDTO> result = dtaCardDAO.getDTACardByHolderId(181);
        logger.info(result != null ? result.toString() : "No result found");
        assert(result != null);
    }
}