package campeonato.exercicio.pontuacao.domain;

import campeonato.exercicio.campeonato.domain.Campeonato;
import campeonato.exercicio.time.domain.Time;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//http://localhost:8080/pontuacao/
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="pontuacao")
public class Pontuacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "time_id")
    private Time timeId;
    @Column(name = "pont")
    private Integer pont;
    @Column(name = "quant_jogos")
    private Integer quantJogos;
    @Column(name = "quant_vitorias")
    private Integer quantVitorias;
    @Column(name = "quant_derrotas")
    private Integer quantDerrotas;
    @Column(name = "quant_empates")
    private Integer quantEmpates;
    @Column(name = "quant_gols_feitos")
    private Integer quantGolsFeitos;
    @Column(name = "quant_gols_sofridos")
    private Integer quantGolsSofridos;
    @ManyToOne
    @JoinColumn(name = "campeonato_id")
    private Campeonato campeonatoId;
}