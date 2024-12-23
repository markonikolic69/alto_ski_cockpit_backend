package ski.alto.cockpit.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ski.alto.cockpit.server.model.Resorts;
import ski.alto.cockpit.server.service.ResortsService;

import java.util.List;

@RestController
public class ResortsController {

    @Autowired
    ResortsService resortsService;

    @PostMapping("/resorts")
    @CrossOrigin(origins = {"http://94.127.4.240:4200", "http://reciklomatmobapp.suprabit.rs:8081/", "http://localhost:4200", "http://localhost:8081", "https://cockpit.alto.ski", "http://65.21.206.110:8081", "https://skiclubgb-cockpit-client-ec942f8fa647.herokuapp.com", "https://skiclub.alto.ski", "https://alto-ski-cockpit-client-393cf5cd662a.herokuapp.com"})
    public List<Resorts> getAllResorts() {
        return resortsService.getAllResorts();
    }
    
    
    @PostMapping("/skiclubResorts")
    @CrossOrigin(origins = {"http://94.127.4.240:4200", "http://reciklomatmobapp.suprabit.rs:8081/", "http://localhost:4200", "http://localhost:8081", "https://cockpit.alto.ski", "http://65.21.206.110:8081", "https://skiclubgb-cockpit-client-ec942f8fa647.herokuapp.com", "https://skiclub.alto.ski", "https://alto-ski-cockpit-client-393cf5cd662a.herokuapp.com"})
    public List<Resorts> getAllSkiclubResorts() {
        return resortsService.getAllSkiclubResorts();
    }

}
