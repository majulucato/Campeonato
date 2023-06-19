package campeonato.exercicio.campeonato.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Table(name= "campeonato")
public class Campeonato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "campeonato_id")
    private Long campeonatoId;
    @NotEmpty
    @Column(name = "nome")
    private String nome;
    @NonNull
    @Column(name = "ano")
    private Integer ano;
    @Column(name = "iniciado")
    private Boolean iniciado=false;
    @Column(name = "finalizado")
    private Boolean finalizado=false;

}
