package campeonato.exercicio.domain;

import jakarta.persistence.*;
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
public class Campeonatos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nome")
    private String nome;
    @NonNull
    @Column(name = "ano")
    private Integer ano;
    @Column(name = "status", nullable = false)
    private Boolean status;  //  1-Iniciado   0-Finalizado

}
