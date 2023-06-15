package campeonato.exercicio.jogo.domain;

import campeonato.exercicio.campeonato.domain.Campeonato;
import campeonato.exercicio.time.domain.Time;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Table(name="jogos")
public class Jogo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "nome_part")
    private String nomePart;   // time_mand x time_visit
    @Column(name = "gols_mand")
    @Min(value = 0)
    private Integer golsMand;
    @Column(name = "gols_visit")
    @Min(value = 0)
    private Integer golsVisit;
    @ManyToOne
    @JoinColumn(name = "time_mandante_id")
    private Time timeMandante;
    @ManyToOne
    @JoinColumn(name = "time_visitante_id")
    private Time timeVisitante;
    @ManyToOne
    @JoinColumn(name = "campeonato_id")
    Campeonato campeonatoId;   //se null -> amistoso
    @Column(name = "status_partida")
    @NotNull
    private Boolean statusPartida;
}
