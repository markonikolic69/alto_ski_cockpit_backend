package ski.alto.cockpit.server.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "admin_cockpit_users")
public class AdminCockpitUsersDTO {
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "email")
    private String email;
    @Column(name = "password_digest")
    private String passwordDigest;
    @Column(name = "user_type")
    private String userType;
    @Column(name = "first_name")
    private String first_name;
    @Column(name = "last_name")
    private String last_name;
}