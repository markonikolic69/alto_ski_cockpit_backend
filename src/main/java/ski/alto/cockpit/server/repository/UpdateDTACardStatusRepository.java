package ski.alto.cockpit.server.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import ski.alto.cockpit.server.model.DTACardStatus;

public interface UpdateDTACardStatusRepository extends JpaRepository<DTACardStatus, Long>{
	@Transactional
    @Modifying
    @Query("update DTACardStatus set status = ?1 where card_number = ?2")
	Integer blockCardWithCardNumber(String status, String card_number);
}
