package ski.alto.cockpit.server.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ski.alto.cockpit.server.model.AdminCockpitUsersDTO;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class AdminCockpitUsersServiceTest {

    @Autowired
    AdminCockpitUsersService adminCockpitUsersService;

    @Test
    void findByEmailAndPassword() {

//        AdminCockpitUsersDTO user = adminCockpitUsersService.findByEmailAndPasswordDigest(
//                "manager@test.com",
//                "1d0258c2440a8d19e716292b231e3190"
//        );

        //assertNotNull(user);
    }
}