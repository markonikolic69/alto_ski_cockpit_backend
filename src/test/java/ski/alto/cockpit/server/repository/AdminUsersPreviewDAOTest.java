package ski.alto.cockpit.server.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ski.alto.cockpit.server.controller.request.AdminCockpitUserByUserTypeRequest;
import ski.alto.cockpit.server.controller.request.NewAdminCockpitUserRequest;
import ski.alto.cockpit.server.model.AdminUsersPreviewDTO;
import ski.alto.cockpit.server.response.NewAdminCockpitUserResponse;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class AdminUsersPreviewDAOTest {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    AdminUsersPreviewDAO adminUsersPreviewDAO;

    @Test
    void getAdminCockpitUserByUserType() {

        AdminCockpitUserByUserTypeRequest request = new AdminCockpitUserByUserTypeRequest();
        request.setUser_type("consumer");
//        request.setOwnership("SkiClubGB");

        List<AdminUsersPreviewDTO> result = adminUsersPreviewDAO.getAdminCockpitUserByUserType(request);
        logger.info(result != null ? result.toString() : "No result found");
        assert(result != null);
    }

    @Test
    void searchAdminCockpitUsersByEmail() {
        List<AdminUsersPreviewDTO> result = adminUsersPreviewDAO.searchAdminCockpitUsersByEmail("ana@windhill.co.uk", null);
        logger.info(result != null ? result.toString() : "No result found");
        assert(result != null);
    }

    @Test
    void searchAdminCockpitUsersByFirstName() {
        List<AdminUsersPreviewDTO> result = adminUsersPreviewDAO.searchAdminCockpitUsersByFirstName("Ana" ,"SkiClubGB");
        logger.info(result != null ? result.toString() : "No result found");
        assert(result != null);
    }

    @Test
    void searchAdminCockpitUsersByLastName() {
        List<AdminUsersPreviewDTO> result = adminUsersPreviewDAO.searchAdminCockpitUsersByLastName("Davis", null);
        logger.info(result != null ? "" + result.size() : "No result found");
        assert(result != null);
    }

    @Test
    void searchAdminCockpitUsersByID() {
        List<AdminUsersPreviewDTO> result = adminUsersPreviewDAO.searchAdminCockpitUsersByID("5", "SkiClubGB");
        logger.info(result != null ? result.toString() : "No result found");
        assert(result != null);
    }

    @Test
    void getAdminCockpitUserByID() {
        AdminUsersPreviewDTO result = adminUsersPreviewDAO.getAdminCockpitUserByID(5, "SkiClubGB");
        logger.info(result != null ? result.toString() : "No result found");
        assert(result != null);
    }

    @Test
    void addAdminCockpitUser() {

        NewAdminCockpitUserRequest request = new NewAdminCockpitUserRequest();
        request.setId(7);
        request.setEmail("test1@example.com");
        request.setUserType("manager");
        request.setPasswordDigest("$2a$10$sWBYmFRSjk2Q2XMha2D7mez1uP5toTo.nKKTZ.eP6O074HP83IObS");


        NewAdminCockpitUserResponse result = adminUsersPreviewDAO.addAdminCockpitUser(request);
        logger.info(result != null ? result.toString() : "No result found");
        assert(result != null);
    }

    @Test
    void editAdminCockpitUser() {
        NewAdminCockpitUserRequest request = new NewAdminCockpitUserRequest();
        request.setEmail("test@example.com");

        int result = adminUsersPreviewDAO.editAdminCockpitUser(request);
        logger.info(result > 0 ? "" + result : "" + result);
//        assert(result > 0);
    }

    @Test
    void removeAdminCockpitUser() {
        NewAdminCockpitUserRequest request = new NewAdminCockpitUserRequest();
        request.setEmail("test@example.com");

        int result = adminUsersPreviewDAO.removeAdminCockpitUser(request);
        logger.info(result > 0 ? "" + result : "No result found");
//        assert(result > 0);
    }
}