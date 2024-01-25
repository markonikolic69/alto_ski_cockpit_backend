package ski.alto.cockpit.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ski.alto.cockpit.server.model.CockpitResortReports;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CockpitResortReportsRepository extends JpaRepository<CockpitResortReports, Long> {

    public CockpitResortReports save(CockpitResortReports report);

    public List<CockpitResortReports> findByResortIdAndTransactionDateBetween(Integer resortId, LocalDateTime from, LocalDateTime to);
}
