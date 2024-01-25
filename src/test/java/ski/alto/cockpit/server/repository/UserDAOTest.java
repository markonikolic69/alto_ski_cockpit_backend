package ski.alto.cockpit.server.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ski.alto.cockpit.server.model.UserDTO;
import ski.alto.cockpit.server.utility.OwnershipUtil;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDAOTest {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    UserDAO userDAO;

    @Test
    public void getUser() {
        List<UserDTO> result = userDAO.getUserByFirstName("IndividualT",null);
        result.forEach(user -> logger.info(user.toString()));

    }

    @Test
    public void getUserByFirstName() {
        List<UserDTO> result = userDAO.getUserByFirstName("IndividualT",
                OwnershipUtil.parseOwnership(OwnershipUtil.SKICLUB_GB));
        result.forEach(user -> logger.info(user.toString()));

    }

    @org.junit.jupiter.api.Test
    void getUserByLastName() {
        List<UserDTO> result = userDAO.getUserByLastName("Tester",
                OwnershipUtil.parseOwnership(OwnershipUtil.SKICLUB_GB));
        result.forEach(user -> logger.info(user.toString()));

    }

    @org.junit.jupiter.api.Test
    void getUserByEmail() {
        List<UserDTO> result = userDAO.getUserByEmail("skigbtest@gmail.com",
                OwnershipUtil.parseOwnership(OwnershipUtil.SKICLUB_GB));
        result.forEach(user -> logger.info(user.toString()));
    }

    @org.junit.jupiter.api.Test
    void getUserByDOB() {
        List<UserDTO> result = userDAO.getUserByDOB("1990-01-01",
                OwnershipUtil.parseOwnership(OwnershipUtil.SKICLUB_GB));
        result.forEach(user -> logger.info(user.toString()));
    }

    @org.junit.jupiter.api.Test
    void getResortContactPerson() {
        List<UserDTO> result = userDAO.getResortContactPerson(40, "admin");
        result.forEach(user -> logger.info(user.toString()));
    }
}
