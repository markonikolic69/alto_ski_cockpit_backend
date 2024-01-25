package ski.alto.cockpit.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "smart_cards")
public class DTACardStatus {
	@Id
	@Column(name="card_number")
	String card_number;
//	@Enumerated(EnumType.ORDINAL)
	@Column(name="status")
	String status;
}
