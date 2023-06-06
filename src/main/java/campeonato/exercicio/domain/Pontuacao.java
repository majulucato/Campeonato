package campeonato.exercicio.domain;

import jakarta.persistence.*;
import lombok.*;

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
    private Integer id;
    @Column(name = "nome_time")
    private String nomeTime;
    @Column(name = "pont", nullable = false,  columnDefinition = "Integer default 0")
    private Integer pont;
    @Column(name = "quant_vitorias", nullable = false,  columnDefinition = "Integer default 0")
    private Integer quantVitorias;
    @Column(name = "quant_derrotas", nullable = false,  columnDefinition = "Integer default 0")
    private Integer quantDerrotas;
    @Column(name = "quant_gols_feitos", nullable = false,  columnDefinition = "Integer default 0")
    private Integer quantGolsFeitos;
    @Column(name = "quant_gols_sofridos", nullable = false,  columnDefinition = "Integer default 0")
    private Integer quantGolsSofridos;
}
