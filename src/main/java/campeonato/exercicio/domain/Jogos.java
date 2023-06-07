package campeonato.exercicio.domain;

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
public class Jogos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nome_part")
    private String nomePart;// time_mand x time_visit
    @Column(name = "tipo")
    private Boolean tipo;//0-Amistoso 1-Oficial
    @Column(name = "time_mandante")
    private String timeMandante;
    @Column(name = "time_visitante")
    private String timeVisitante;
    @Column(name = "status")
    private Boolean status;//1-iniciado   0-finalizado

}
