package ski.alto.cockpit.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ski.alto.cockpit.server.model.AdminCockpitUsersDTO;

@Repository
public interface AdminCockpitUsersRepository extends JpaRepository<AdminCockpitUsersDTO, Long> {
    AdminCockpitUsersDTO findByPasswordDigest(String passwordDigest);
    AdminCockpitUsersDTO findByEmail(String email);
    AdminCockpitUsersDTO findByEmailAndPasswordDigest(String email, String passwordDigest);
}