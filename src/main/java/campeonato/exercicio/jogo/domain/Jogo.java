package campeonato.exercicio.jogo.domain;

import campeonato.exercicio.campeonato.domain.Campeonato;
import campeonato.exercicio.time.domain.Time;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="jogos")
public class Jogo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "nome_part")
    private String nomePart;// time_mand x time_visit
    @Column(name = "gols_mand")
    private Integer golsMand;
    @Column(name = "gols_visit")
    private Integer golsVisit;
    @ManyToOne
    @JoinColumn(name = "time_mandante_id")
    private Time timeMandante;
    @ManyToOne
    @JoinColumn(name = "time_visitante_id")
    private Time timeVisitante;
    @ManyToOne
    @JoinColumn(name = "campeonato_id")
    Campeonato campeonato;
}
