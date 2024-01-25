package ski.alto.cockpit.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

import javax.persistence.*;

@Data
@AllArgsConstructor
public class UserDTO {
	private Integer id;
    private String name;
    private String surname;
    private String dob;
    private String email;
    private String phone;
    private String address1;
    private String address2;
    private String city;
    private String country;
    private String county_or_state;
    private String postal_code;
    @Setter
    private List<DTACardDTO> owned_cards;
    private List<DTACardDTO> assigned_cards;
    private Boolean credit_card_registered;
    private List<PromoCodeRedemptionDTO> promo_code_redemptions;
    @JsonIgnore
    private String role;
    private String ownership;
}
