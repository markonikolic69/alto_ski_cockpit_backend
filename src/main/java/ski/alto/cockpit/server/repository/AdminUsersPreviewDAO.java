package ski.alto.cockpit.server.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ski.alto.cockpit.server.controller.request.AdminCockpitUserByUserTypeRequest;
import ski.alto.cockpit.server.controller.request.NewAdminCockpitUserRequest;
import ski.alto.cockpit.server.model.AdminCockpitUsersDTO;
import ski.alto.cockpit.server.model.AdminUsersPreviewDTO;
import ski.alto.cockpit.server.model.ResortIdNamePair;
import ski.alto.cockpit.server.response.NewAdminCockpitUserResponse;
import ski.alto.cockpit.server.utility.OwnershipUtil;

@Component
public class AdminUsersPreviewDAO {
	@Autowired
    JdbcTemplate jdbcTemplate;
	
	public List<AdminUsersPreviewDTO> getAdminCockpitUserByUserType(AdminCockpitUserByUserTypeRequest user) {

		List<AdminUsersPreviewDTO> dtaAdminUsers =  null;
		String ownership_parset = OwnershipUtil.parseOwnership(user.getOwnership());

		if (ownership_parset != null) {
			dtaAdminUsers =  jdbcTemplate.query(
					"select u.id user_id, u.email email, u.first_name first_name, u.last_name last_name, u.role user_type, \r\n"
							+ "u.password_digest password_digest, res.name resort_name, res.id resort_id\r\n"
							+ "from users u join resorts res on u.resort_id=res.id where u.role=?::users_roles\r\n"
							+ "and u.ownership=?",
					(rs, rowNum) -> new AdminUsersPreviewDTO(
							null,
							rs.getString("email"),
							rs.getString("first_name"),
							rs.getString("last_name"),
							rs.getString("password_digest"),
							rs.getString("user_type"),
							rs.getInt("user_id"),
							rs.getString("resort_name"),
							rs.getInt("resort_id")),
					user.getUser_type(), ownership_parset
			);
		} else {
			dtaAdminUsers =  jdbcTemplate.query(
					"select u.id user_id, u.email email, u.first_name first_name, u.last_name last_name, u.role user_type, \r\n"
							+ "u.password_digest password_digest, res.name resort_name, res.id resort_id\r\n"
							+ "from users u join resorts res on u.resort_id=res.id where u.role=?::users_roles\r\n",
					(rs, rowNum) -> new AdminUsersPreviewDTO(
							null,
							rs.getString("email"),
							rs.getString("first_name"),
							rs.getString("last_name"),
							rs.getString("password_digest"),
							rs.getString("user_type"),
							rs.getInt("user_id"),
							rs.getString("resort_name"),
							rs.getInt("resort_id")),
					user.getUser_type()
			);
		}
    	
    	return dtaAdminUsers;
    }

	public List<AdminUsersPreviewDTO> searchAdminCockpitUsersByEmail(String email, String ownership) {

    	List<AdminUsersPreviewDTO> dtaAdminUsers =  null;
    	
    	String ownership_parset = OwnershipUtil.parseOwnership(ownership);

		if (ownership_parset != null) {
			dtaAdminUsers = jdbcTemplate.query(
					"select acu.id id, acu.email email, acu.password_digest password_digest, acu.user_type user_type, acu.first_name first_name, \r\n"
							+ "acu.last_name last_name, u.id users_id, r.name resort_name, r.id resort_id\r\n"
							+ "from admin_cockpit_users acu left outer join users u on acu.users_id=u.id left outer join resorts r on u.resort_id=r.id \r\n"
							+ "where LOWER(acu.email) LIKE LOWER('%'||?||'%') and acu.ownership=?\r\n"
							+ "order by id desc",
					(rs, rowNum) -> new AdminUsersPreviewDTO(
							rs.getInt("id"),
							rs.getString("email"),
							rs.getString("first_name"),
							rs.getString("last_name"),
							rs.getString("password_digest"),
							rs.getString("user_type"),
							rs.getInt("users_id"),
							rs.getString("resort_name"),
							rs.getInt("resort_id")),
					email, ownership_parset
			);
		} else {
			dtaAdminUsers = jdbcTemplate.query(
					"select acu.id id, acu.email email, acu.password_digest password_digest, acu.user_type user_type, acu.first_name first_name, \r\n"
							+ "acu.last_name last_name, u.id users_id, r.name resort_name, r.id resort_id\r\n"
							+ "from admin_cockpit_users acu left outer join users u on acu.users_id=u.id left outer join resorts r on u.resort_id=r.id where LOWER(acu.email) LIKE LOWER('%'||?||'%')\r\n"
							+ "order by id desc",
					(rs, rowNum) -> new AdminUsersPreviewDTO(
							rs.getInt("id"),
							rs.getString("email"),
							rs.getString("first_name"),
							rs.getString("last_name"),
							rs.getString("password_digest"),
							rs.getString("user_type"),
							rs.getInt("users_id"),
							rs.getString("resort_name"),
							rs.getInt("resort_id")),
					email
			);
		}
    	
    	return dtaAdminUsers;
    }
	
	public List<AdminUsersPreviewDTO> searchAdminCockpitUsersByFirstName(String first_name, String ownership) {

		List<AdminUsersPreviewDTO> dtaAdminUsers = null;
		
		String ownership_parset = OwnershipUtil.parseOwnership(ownership);

		if (ownership_parset != null) {
			dtaAdminUsers =  jdbcTemplate.query(
					"select acu.id id, acu.email email, acu.password_digest password_digest, acu.user_type user_type, acu.first_name first_name, \r\n"
							+ "acu.last_name last_name, u.id users_id, r.name resort_name, r.id resort_id\r\n"
							+ "from admin_cockpit_users acu left outer join users u on acu.users_id=u.id left outer join resorts r on u.resort_id=r.id where LOWER(acu.first_name) LIKE LOWER('%'||?||'%') and acu.ownership=?\r\n"
							+ "order by id desc",
					(rs, rowNum) -> new AdminUsersPreviewDTO(
							rs.getInt("id"),
							rs.getString("email"),
							rs.getString("first_name"),
							rs.getString("last_name"),
							rs.getString("password_digest"),
							rs.getString("user_type"),
							rs.getInt("users_id"),
							rs.getString("resort_name"),
							rs.getInt("resort_id")),
					first_name, ownership_parset
			);
		} else {
			dtaAdminUsers =  jdbcTemplate.query(
					"select acu.id id, acu.email email, acu.password_digest password_digest, acu.user_type user_type, acu.first_name first_name, \r\n"
							+ "acu.last_name last_name, u.id users_id, r.name resort_name, r.id resort_id\r\n"
							+ "from admin_cockpit_users acu left outer join users u on acu.users_id=u.id left outer join resorts r on u.resort_id=r.id where LOWER(acu.first_name) LIKE LOWER('%'||?||'%') \r\n"
							+ "order by id desc",
					(rs, rowNum) -> new AdminUsersPreviewDTO(
							rs.getInt("id"),
							rs.getString("email"),
							rs.getString("first_name"),
							rs.getString("last_name"),
							rs.getString("password_digest"),
							rs.getString("user_type"),
							rs.getInt("users_id"),
							rs.getString("resort_name"),
							rs.getInt("resort_id")),
					first_name
			);
		}
    	
    	return dtaAdminUsers;
    }
	
	public List<AdminUsersPreviewDTO> searchAdminCockpitUsersByLastName(String last_name, String ownership) {

		List<AdminUsersPreviewDTO> dtaAdminUsers = null;
		
		String ownership_parset = OwnershipUtil.parseOwnership(ownership);

		if (ownership_parset != null) {
			dtaAdminUsers =  jdbcTemplate.query(
					"select acu.id id, acu.email email, acu.password_digest password_digest, acu.user_type user_type, acu.first_name first_name, \r\n"
							+ "acu.last_name last_name, u.id users_id, r.name resort_name, r.id resort_id\r\n"
							+ "from admin_cockpit_users acu left outer join users u on acu.users_id=u.id left outer join resorts r on u.resort_id=r.id where LOWER(acu.last_name) LIKE LOWER('%'||?||'%') and acu.ownership=?\r\n"
							+ "order by id desc",
					(rs, rowNum) -> new AdminUsersPreviewDTO(
							rs.getInt("id"),
							rs.getString("email"),
							rs.getString("first_name"),
							rs.getString("last_name"),
							rs.getString("password_digest"),
							rs.getString("user_type"),
							rs.getInt("users_id"),
							rs.getString("resort_name"),
							rs.getInt("resort_id")),
					last_name, ownership_parset
			);
		} else {
			dtaAdminUsers =  jdbcTemplate.query(
					"select acu.id id, acu.email email, acu.password_digest password_digest, acu.user_type user_type, acu.first_name first_name, \r\n"
							+ "acu.last_name last_name, u.id users_id, r.name resort_name, r.id resort_id\r\n"
							+ "from admin_cockpit_users acu left outer join users u on acu.users_id=u.id left outer join resorts r on u.resort_id=r.id where LOWER(acu.last_name) LIKE LOWER('%'||?||'%') \r\n"
							+ "order by id desc",
					(rs, rowNum) -> new AdminUsersPreviewDTO(
							rs.getInt("id"),
							rs.getString("email"),
							rs.getString("first_name"),
							rs.getString("last_name"),
							rs.getString("password_digest"),
							rs.getString("user_type"),
							rs.getInt("users_id"),
							rs.getString("resort_name"),
							rs.getInt("resort_id")),
					last_name
			);
		}
    	
    	return dtaAdminUsers;
    }
	
	public List<AdminUsersPreviewDTO> searchAdminCockpitUsersByID(String id, String ownership) {

		List<AdminUsersPreviewDTO> dtaAdminUsers = null;
		
		String ownership_parset = OwnershipUtil.parseOwnership(ownership);

		if ( ownership_parset != null) {
			dtaAdminUsers =  jdbcTemplate.query(
					"select acu.id id, acu.email email, acu.password_digest password_digest, acu.user_type user_type, acu.first_name first_name, \r\n"
							+ "acu.last_name last_name, u.id users_id, r.name resort_name, r.id resort_id\r\n"
							+ "from admin_cockpit_users acu left outer join users u on acu.users_id=u.id left outer join resorts r on u.resort_id=r.id\r\n "
							+ "where LOWER(acu.id::varchar) LIKE LOWER('%'||?||'%') and acu.ownership=?\r\n"
							+ "order by id desc",
					(rs, rowNum) -> new AdminUsersPreviewDTO(
							rs.getInt("id"),
							rs.getString("email"),
							rs.getString("first_name"),
							rs.getString("last_name"),
							rs.getString("password_digest"),
							rs.getString("user_type"),
							rs.getInt("users_id"),
							rs.getString("resort_name"),
							rs.getInt("resort_id")),
					id, ownership_parset
			);
		} else {
			dtaAdminUsers =  jdbcTemplate.query(
					"select acu.id id, acu.email email, acu.password_digest password_digest, acu.user_type user_type, acu.first_name first_name, \r\n"
							+ "acu.last_name last_name, u.id users_id, r.name resort_name, r.id resort_id\r\n"
							+ "from admin_cockpit_users acu left outer join users u on acu.users_id=u.id left outer join resorts r on u.resort_id=r.id\r\n "
							+ "where LOWER(acu.id::varchar) LIKE LOWER('%'||?||'%') \r\n"
							+ "order by id desc",
					(rs, rowNum) -> new AdminUsersPreviewDTO(
							rs.getInt("id"),
							rs.getString("email"),
							rs.getString("first_name"),
							rs.getString("last_name"),
							rs.getString("password_digest"),
							rs.getString("user_type"),
							rs.getInt("users_id"),
							rs.getString("resort_name"),
							rs.getInt("resort_id")),
					id
			);
		}
    	
    	return dtaAdminUsers;
    }
	
	public AdminUsersPreviewDTO getAdminCockpitUserByID(Integer id, String ownership) {

		AdminUsersPreviewDTO dtaAdminUser = null;

		if (OwnershipUtil.parseOwnership(ownership) == null ) {
			// not using ownership to allow Alto users to log in
			List<AdminUsersPreviewDTO> results =  jdbcTemplate.query(
					"select acu.id id, acu.email email, acu.password_digest password_digest, acu.user_type user_type, acu.first_name first_name, \r\n"
							+ "acu.last_name last_name, u.id users_id, r.name resort_name, r.id resort_id\r\n"
							+ "from admin_cockpit_users acu left outer join users u on acu.users_id=u.id left outer join resorts r on u.resort_id=r.id where acu.id=? and acu.ownership=?",
					(rs, rowNum) -> new AdminUsersPreviewDTO(
							rs.getInt("id"),
							rs.getString("email"),
							rs.getString("first_name"),
							rs.getString("last_name"),
							rs.getString("password_digest"),
							rs.getString("user_type"),
							rs.getInt("users_id"),
							rs.getString("resort_name"),
							rs.getInt("resort_id")),
					id, ownership
			);
			if (!results.isEmpty()) {
				dtaAdminUser = results.get(0);
			}
		} else {
			List<AdminUsersPreviewDTO> results = jdbcTemplate.query(
					"select acu.id id, acu.email email, acu.password_digest password_digest, acu.user_type user_type, acu.first_name first_name, \r\n"
							+ "acu.last_name last_name, u.id users_id, r.name resort_name, r.id resort_id\r\n"
							+ "from admin_cockpit_users acu left outer join users u on acu.users_id=u.id left outer join resorts r on u.resort_id=r.id where acu.id=? and acu.ownership=?",
					(rs, rowNum) -> new AdminUsersPreviewDTO(
							rs.getInt("id"),
							rs.getString("email"),
							rs.getString("first_name"),
							rs.getString("last_name"),
							rs.getString("password_digest"),
							rs.getString("user_type"),
							rs.getInt("users_id"),
							rs.getString("resort_name"),
							rs.getInt("resort_id")),
					id, OwnershipUtil.parseOwnership(OwnershipUtil.SKICLUB_GB)
			);
			if (!results.isEmpty()) {
				dtaAdminUser = results.get(0);
			}
		}


    	return dtaAdminUser;
    }
	
	private boolean emailExists(NewAdminCockpitUserRequest user) {
		boolean dtaAdminUserEmailExists =  jdbcTemplate.query(
                "select email from admin_cockpit_users where email=?",
                (rs, rowNum) -> rs.getString("email"),
                user.getEmail()
        ).size() > 0;
    	
    	
    	return dtaAdminUserEmailExists;
	}
	
	public NewAdminCockpitUserResponse addAdminCockpitUser(NewAdminCockpitUserRequest user) {
		int success;
		String message = "OK";
		String ownership = user.getResort_name() == null || user.getResort_name().equals("") ? "SkiClub" : null;
		if(emailExists(user)) {
			success = -1;
			message = "Email already exists!";
		}
		else
			success =  jdbcTemplate.update(
    		    "INSERT INTO public.admin_cockpit_users (email, first_name, last_name, password_digest, user_type, id, users_id, ownership) VALUES (?, ?, ?, ?, ?, default, ?, ?)",
    		    user.getEmail(), user.getFirst_name(), user.getLast_name(), user.getPasswordDigest(), user.getUserType(), user.getUsers_id(), ownership
    		);
    	return new NewAdminCockpitUserResponse(success, message);
    }
	
	public int editAdminCockpitUser(NewAdminCockpitUserRequest user) {
    	int success =  jdbcTemplate.update(
    		    "UPDATE public.admin_cockpit_users SET email=?, password_digest=?, user_type=?, first_name=?, last_name=?\r\n"
    		    + "where id=?",
    		    user.getEmail(), user.getPasswordDigest(), user.getUserType(), user.getFirst_name(), user.getLast_name(), user.getId()
    		);
    	return success;
    }
	
	public int removeAdminCockpitUser(NewAdminCockpitUserRequest user) {
    	int success =  jdbcTemplate.update(
    		    "delete from admin_cockpit_users \r\n"
    		    + "where id=?",
    		    user.getId()
    		);
    	return success;
    }
}
