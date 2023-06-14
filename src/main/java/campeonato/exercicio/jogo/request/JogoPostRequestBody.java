package campeonato.exercicio.jogo.request;

import lombok.Data;

@Data
public class JogoPostRequestBody {
    Long id;
    String nomePart;// time_mand x time_visit
    Integer golsMand;
    Integer golsVisit;
    Long timeMandante;
    Long timeVisitante;
    Long campeonato;
}
