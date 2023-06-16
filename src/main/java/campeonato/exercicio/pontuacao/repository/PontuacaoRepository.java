package campeonato.exercicio.pontuacao.repository;

import campeonato.exercicio.pontuacao.domain.Pontuacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PontuacaoRepository extends JpaRepository<Pontuacao, Integer> {
    @Query(nativeQuery = true,
            value = "SELECT count(*) > 0 " +
                    "FROM pontuacao p " +
                    "WHERE p.time_id = :timeId ")
    List<Pontuacao> findByTime(@Param("timeId") String timeId);

    @Query(nativeQuery = true,
            value = "SELECT * " +
                    "FROM pontuacao p " +
                    "WHERE  p.time_id = :timeId " +
                    "AND p.campeonato_id = :campeonatoId")
    Pontuacao findByCampeonatoAndTime(@Param("campeonatoId") Long campeonatoId,
                                      @Param("timeId") Long timeId);

}
