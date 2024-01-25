package ski.alto.cockpit.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ski.alto.cockpit.server.model.Resorts;
import ski.alto.cockpit.server.repository.ResortsRepository;

import java.util.List;

@Service
public class ResortsService {

    @Autowired
    ResortsRepository resortsRepository;

    public List<Resorts> getAllResorts() {
        return resortsRepository.findAllByOrderByIdAsc();
//        return resortsRepository.findAll();
    }
}
