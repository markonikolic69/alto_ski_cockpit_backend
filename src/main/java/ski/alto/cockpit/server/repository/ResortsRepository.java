package ski.alto.cockpit.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ski.alto.cockpit.server.model.Resorts;

import java.util.List;

@Repository
public interface ResortsRepository extends JpaRepository<Resorts, Long> {

    public List<Resorts> findByName(String name);
    public List<Resorts> findAllByOrderByIdAsc();
}
