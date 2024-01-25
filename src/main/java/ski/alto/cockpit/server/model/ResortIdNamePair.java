package ski.alto.cockpit.server.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResortIdNamePair {
	private Integer resort_id;
	private String resort_name;
}
