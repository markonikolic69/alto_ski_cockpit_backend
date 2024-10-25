package ski.alto.cockpit.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ski.alto.cockpit.server.model.Skiclub_locations;

@Repository
public interface Skiclub_locationsRepository extends JpaRepository<Skiclub_locations, Long> {

	public List<Skiclub_locations> findAllByOrderByIdAsc();
}
