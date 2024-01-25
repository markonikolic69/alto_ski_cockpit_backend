package ski.alto.cockpit.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ski.alto.cockpit.server.model.CockpitResortReports;
import ski.alto.cockpit.server.model.SmartCardUsages;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SmartCardUsagesRepository extends JpaRepository<SmartCardUsages, Long> {

    public List<SmartCardUsages> findTop100ByOrderByStartDateDesc ();
    public List<SmartCardUsages> findTop100ByResortIdOrderByStartDateDesc (Integer resortId);
    public List<SmartCardUsages> findByStartDateBetweenOrderByStartDateDesc (LocalDateTime from, LocalDateTime to);

    public List<SmartCardUsages> findByResortIdAndStartDateBetweenOrderByStartDateDesc (Integer resortId, LocalDateTime from, LocalDateTime to);
}
