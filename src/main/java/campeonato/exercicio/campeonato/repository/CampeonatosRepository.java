package campeonato.exercicio.campeonato.repository;

import campeonato.exercicio.campeonato.domain.Campeonato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CampeonatosRepository extends JpaRepository<Campeonato, Integer> {

    @Query(nativeQuery = true,
            value = "SELECT COUNT(*)>0 " +
                    "FROM campeonato c " +
                    "WHERE c.nome = :campeonato_nome " +
                    "AND c.ano = :campeonato_ano")
    boolean existsByNomeAndAno(@Param("campeonato_nome") String nome,@Param("campeonato_ano") Integer ano);

    @Query(nativeQuery = true,
            value = "SELECT COUNT(*) > 0 " +
                    "FROM campeonato c " +
                    "WHERE c.id = :campeonato_id " +
                    "AND c.iniciado = true")
    boolean statusIniciado(@Param("campeonato_id")Long id);

    @Query(nativeQuery = true,
            value = "SELECT COUNT(*) > 0 " +
                    "FROM campeonato c " +
                    "WHERE c.id = :campeonato_id " +
                    "AND c.finalizado = true")
    boolean statusFinalizado(@Param("campeonato_id ")Long id);
}
