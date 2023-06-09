package campeonato.exercicio.campeonato.domain;

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
public class Campeonato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "nome")
    private String nome;
    @NonNull
    @Column(name = "ano")
    private Integer ano;
    @Column(name = "iniciado")
    private Boolean iniciado = false;  //  1-Iniciado   0-Finalizado
    @Column(name = "finalizado")
    private Boolean finalizado = false;
}
