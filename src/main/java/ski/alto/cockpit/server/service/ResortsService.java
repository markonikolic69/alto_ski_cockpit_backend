package ski.alto.cockpit.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ski.alto.cockpit.server.model.Resorts;
import ski.alto.cockpit.server.model.Skiclub_locations;
import ski.alto.cockpit.server.repository.ResortsRepository;
import ski.alto.cockpit.server.repository.Skiclub_locationsRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResortsService {

    @Autowired
    ResortsRepository resortsRepository;
    
    @Autowired
    Skiclub_locationsRepository skiclub_resortsRepository;

    public List<Resorts> getAllResorts() {
        return resortsRepository.findAllByOrderByIdAsc();
//        return resortsRepository.findAll();
    }
    
    public List<Resorts> getAllSkiclubResorts() {
    	List<Resorts> to_return = new ArrayList<Resorts>();
    	List<Skiclub_locations> skiclub_resorts =  skiclub_resortsRepository.findAllByOrderByIdAsc();
    	
    	for(Skiclub_locations current_resort : skiclub_resorts) {
    		Resorts to_add = new Resorts();
    		to_add.setId(current_resort.getId());
    		to_add.setName(current_resort.getTitle_rendered());
    		to_return.add(to_add);
    	}
    	return to_return;
    }
}
