package ski.alto.cockpit.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(/*exclude = {DataSourceAutoConfiguration.class }*/)
public class AltoskiAdminCockpitServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AltoskiAdminCockpitServerApplication.class, args);
	}

}
