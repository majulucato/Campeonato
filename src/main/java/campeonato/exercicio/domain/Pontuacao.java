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
    @Column(name = "pont")
    private Integer pont;
    @Column(name = "quant_vitorias")
    private Integer quantVitorias;
    @Column(name = "quant_derrotas")
    private Integer quantDerrotas;
    @Column
    private Integer quantEmpates;
    @Column(name = "quant_gols_feitos")
    private Integer quantGolsFeitos;
    @Column(name = "quant_gols_sofridos")
    private Integer quantGolsSofridos;
}