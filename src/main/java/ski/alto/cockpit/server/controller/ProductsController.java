package ski.alto.cockpit.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ski.alto.cockpit.server.controller.request.ProductsRequest;
import ski.alto.cockpit.server.model.ProductDTO;
import ski.alto.cockpit.server.model.SeasonDTO;
import ski.alto.cockpit.server.repository.ProductDAO;
import ski.alto.cockpit.server.service.ProductsService;

import java.util.List;

@RestController
public class ProductsController {

    @Autowired
    ProductsService productsService;

    @PostMapping("/products")
    @CrossOrigin(origins = {"http://94.127.4.240:4200", "http://reciklomatmobapp.suprabit.rs:8081/", "http://localhost:4200", "http://localhost:8081", "https://cockpit.alto.ski", "http://65.21.206.110:8081", "https://skiclubgb-cockpit-client-ec942f8fa647.herokuapp.com", "https://skiclub.alto.ski", "https://alto-ski-cockpit-client-393cf5cd662a.herokuapp.com"})
    public List<ProductDTO> getAllProducts(@RequestBody ProductsRequest request) {
        return productsService.getProductsByResortId(request.getResortId());
    }

    @PostMapping("/products_by_seasons")
    @CrossOrigin(origins = {"http://94.127.4.240:4200", "http://reciklomatmobapp.suprabit.rs:8081/", "http://localhost:4200", "http://localhost:8081", "https://cockpit.alto.ski", "http://65.21.206.110:8081", "https://skiclubgb-cockpit-client-ec942f8fa647.herokuapp.com", "https://skiclub.alto.ski", "https://alto-ski-cockpit-client-393cf5cd662a.herokuapp.com"})
    public List<SeasonDTO> getAllProductsBySeasons(@RequestBody ProductsRequest request) {
        return productsService.getSeasonsByResortId(request.getResortId());
    }

}
