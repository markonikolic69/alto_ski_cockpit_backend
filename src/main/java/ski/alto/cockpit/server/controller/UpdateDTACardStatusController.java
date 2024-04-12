package ski.alto.cockpit.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ski.alto.cockpit.server.model.DTACardStatus;
import ski.alto.cockpit.server.repository.UpdateDTACardStatusRepository;

@RestController
public class UpdateDTACardStatusController {
	@Autowired
    UpdateDTACardStatusRepository updateCardStatusRepository;

//    @CrossOrigin(origins = "http://94.127.4.240:4200")	//	SERVER CONFIG
    @CrossOrigin(origins = {"http://94.127.4.240:4200", "http://localhost:8081", "https://cockpit.alto.ski", "http://65.21.206.110:8081", "https://skiclubgb-cockpit-client-ec942f8fa647.herokuapp.com", "https://skiclub.alto.ski", "https://alto-ski-cockpit-client-393cf5cd662a.herokuapp.com"})
    @PostMapping("/change_card_status")
    public Integer updateDTACardStatus(@RequestBody DTACardStatus request) {

        boolean result = false;
        String status = request.getStatus().equals("active") ? "blocked" : "active";
        Integer card_status = updateCardStatusRepository.blockCardWithCardNumber(request.getCard_number(), request.getStatus());

        if (card_status != null) {
            result = true;
        }

        return card_status;
    }
}
