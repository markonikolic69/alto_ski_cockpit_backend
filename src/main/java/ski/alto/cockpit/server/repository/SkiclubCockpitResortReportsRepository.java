package ski.alto.cockpit.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ski.alto.cockpit.server.model.SkiclubCockpitResortReports;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface SkiclubCockpitResortReportsRepository extends JpaRepository<SkiclubCockpitResortReports, Long>{
	
    public SkiclubCockpitResortReports save(SkiclubCockpitResortReports report);

    public List<SkiclubCockpitResortReports> findByResortIdAndTransactionDateBetween(Integer resortId, LocalDateTime from, LocalDateTime to);

}
