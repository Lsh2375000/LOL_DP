package dp.lol.lol_dp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LolDpApplication {

	public static void main(String[] args) {
		SpringApplication.run(LolDpApplication.class, args);
	}

}
