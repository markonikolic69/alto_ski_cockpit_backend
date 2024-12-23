package ski.alto.cockpit.server.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ski.alto.cockpit.server.model.DTACardDTO;
import ski.alto.cockpit.server.repository.DTACardDAO;

@RestController
public class DTACardsController {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    @Autowired
    DTACardDAO dtaCardsDAO;


    @CrossOrigin(origins = {"http://94.127.4.240:4200", "http://reciklomatmobapp.suprabit.rs:8081/", "http://localhost:4200", "http://localhost:8081", "https://cockpit.alto.ski", "http://65.21.206.110:8081", "https://skiclubgb-cockpit-client-ec942f8fa647.herokuapp.com", "https://skiclub.alto.ski", "https://alto-ski-cockpit-client-393cf5cd662a.herokuapp.com"})
    @GetMapping("/dta_cards")
    public List<DTACardDTO> getUsers(@RequestParam String card_number) {
    	return dtaCardsDAO.getDTACardByCardNumber(card_number);
    }
}
