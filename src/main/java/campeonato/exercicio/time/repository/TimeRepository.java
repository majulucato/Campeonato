package campeonato.exercicio.time.repository;

import campeonato.exercicio.time.domain.Time;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeRepository extends JpaRepository<Time, Integer> {

}