package ski.alto.cockpit.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ski.alto.cockpit.server.model.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

}
