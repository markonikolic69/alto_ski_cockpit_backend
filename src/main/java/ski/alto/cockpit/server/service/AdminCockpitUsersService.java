package ski.alto.cockpit.server.service;

import ski.alto.cockpit.server.model.AdminCockpitUsersDTO;

public interface AdminCockpitUsersService {

	public AdminCockpitUsersDTO findByEmail(String email);
	public AdminCockpitUsersDTO findByPasswordDigest(String email);
    public AdminCockpitUsersDTO findByEmailAndPasswordDigest(String email, String password);

}
