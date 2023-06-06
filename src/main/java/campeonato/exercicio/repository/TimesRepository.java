package campeonato.exercicio.repository;

import campeonato.exercicio.domain.Times;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimesRepository extends JpaRepository<Times, Integer> {

}