package ski.alto.cockpit.server.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ski.alto.cockpit.server.model.AdminCockpitUsersDTO;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class AdminCockpitUsersRepositoryTest {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    AdminCockpitUsersRepository adminCockpitUsersRepository;

    @Test
    void findByPasswordDigest() {
        AdminCockpitUsersDTO result = adminCockpitUsersRepository.findByPasswordDigest("$2a$10$xpiZk2zp9q3q2a.8g0g3au2KZ.0a4hWa4Lxc8HHBmIFpio5rZjvUq");
        logger.info(result != null ? result.toString() : "No result found");
        assert(result != null);
    }

    @Test
    void findByEmail() {
        AdminCockpitUsersDTO result = adminCockpitUsersRepository.findByEmail("ana@windhill.co.uk");
        logger.info(result != null ? result.toString() : "No result found");
        assert(result != null);
    }

    @Test
    void findByEmailAndPasswordDigest() {
        AdminCockpitUsersDTO result = adminCockpitUsersRepository.findByEmailAndPasswordDigest("ana@windhill.co.uk",
                "$2a$10$xpiZk2zp9q3q2a.8g0g3au2KZ.0a4hWa4Lxc8HHBmIFpio5rZjvUq");
        logger.info(result != null ? result.toString() : "No result found");
        assert(result != null);
    }
}