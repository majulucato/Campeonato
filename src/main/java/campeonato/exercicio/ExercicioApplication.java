package campeonato.exercicio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "campeonato.exercicio")
public class ExercicioApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExercicioApplication.class, args);
	}

}
