package ski.alto.cockpit.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;


@Data
@AllArgsConstructor
public class AdminUsersPreviewDTO {
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "email")
    private String email;
    @Column(name = "first_name")
    private String first_name;
    @Column(name = "last_name")
    private String last_name;
    @Column(name = "password_digest")
    private String passwordDigest;
    @Column(name = "user_type")
    private String userType;
    private Integer users_id;
    private String resort_name;
    private Integer resort_id;
}