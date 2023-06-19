package campeonato.exercicio.jogo.repository;

import campeonato.exercicio.jogo.domain.Jogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JogoRepository extends JpaRepository<Jogo,Integer> {
    @Query(nativeQuery = true,
            value = "SELECT count(*) > 0 " +
                    "FROM jogos j " +
                    "WHERE j.campeonato_id = :campeonatoId " +
                    "AND j.time_mandante_id = :timeMandante " +
                    "AND j.time_visitante_id = :timeVisitante")
    boolean existsJogo(@Param("campeonatoId") Long campeonatoId,
                        @Param("timeMandante") Long timeMandante,
                        @Param("timeVisitante") Long timeVisitante);
    @Query(nativeQuery = true,
            value = "SELECT count(*)>0 " +
                    "FROM jogos j " +
                    "WHERE j.campeonato_id = :campeonatoId " +
                    "AND j.status_partida = false")
    boolean finishJogos(@Param("campeonatoId") Long campeonatoId);

}
