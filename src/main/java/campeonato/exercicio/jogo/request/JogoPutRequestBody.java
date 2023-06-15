package campeonato.exercicio.jogo.request;

import campeonato.exercicio.campeonato.domain.Campeonato;
import campeonato.exercicio.time.domain.Time;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JogoPutRequestBody {
    Long id;
    String nomePart;// time_mand x time_visit
    @Min(0)
    Integer golsMand;
    @Min(0)
    Integer golsVisit;
    Long timeMandante;
    Long timeVisitante;
    Long campeonato;
    @NotNull
    Boolean statusPartida;
}
