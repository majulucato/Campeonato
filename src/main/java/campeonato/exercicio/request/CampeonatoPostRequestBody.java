package campeonato.exercicio.request;

import campeonato.exercicio.domain.Campeonatos;
import lombok.Data;

import java.util.List;

@Data
public class CampeonatoPostRequestBody {
    Integer id;
    String nome;
    Integer ano;
    List<Campeonatos> times;
    Boolean status;  //  I-iniciado   F-finalizado
}
