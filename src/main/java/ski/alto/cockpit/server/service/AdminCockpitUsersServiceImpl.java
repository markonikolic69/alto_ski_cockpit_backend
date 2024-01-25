package ski.alto.cockpit.server.service;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ski.alto.cockpit.server.model.AdminCockpitUsersDTO;
import ski.alto.cockpit.server.repository.AdminCockpitUsersRepository;

@Service
public class AdminCockpitUsersServiceImpl implements AdminCockpitUsersService {

    @Autowired
    AdminCockpitUsersRepository adminCockpitUsersRepository;

    @Override
    public AdminCockpitUsersDTO findByEmail(String email) { return adminCockpitUsersRepository.findByEmail(email); }

    @Override
    public AdminCockpitUsersDTO findByPasswordDigest(String passwordDigest) { return adminCockpitUsersRepository.findByPasswordDigest(passwordDigest); }
    
    @Override
    public AdminCockpitUsersDTO findByEmailAndPasswordDigest(String email, String password) {

        String passwordDigest = password;
        //TODO: implement password digest calculation
        AdminCockpitUsersDTO u = adminCockpitUsersRepository.findByEmail(email);
        System.out.println(u);
        System.out.println(passwordDigest);

        return adminCockpitUsersRepository.findByEmailAndPasswordDigest(email, passwordDigest);
    }

}
