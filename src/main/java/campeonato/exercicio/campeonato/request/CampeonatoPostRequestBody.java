package campeonato.exercicio.campeonato.request;

import campeonato.exercicio.campeonato.domain.Campeonato;
import lombok.Data;

import java.util.List;

@Data
public class CampeonatoPostRequestBody {
    Integer id;
    String nome;
    Integer ano;
    List<Campeonato> times;
    Boolean status;  //  I-iniciado   F-finalizado
}
