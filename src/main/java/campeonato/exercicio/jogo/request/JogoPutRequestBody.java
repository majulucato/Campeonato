package campeonato.exercicio.jogo.request;

import campeonato.exercicio.campeonato.domain.Campeonato;
import campeonato.exercicio.time.domain.Time;
import lombok.Data;

@Data
public class JogoPutRequestBody {
    Long id;
    String nomePart;// time_mand x time_visit
    Time timeMandante;
    Time timeVisitante;
    Campeonato campeonato;
}
