package campeonato.exercicio.campeonato.repository;

import campeonato.exercicio.campeonato.domain.Campeonato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampeonatosRepository extends JpaRepository<Campeonato, Integer> {

    boolean existsByNomeAndAno(String nome,Integer ano);

    //boolean existsByTime(String time);

}
