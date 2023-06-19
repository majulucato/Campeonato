package campeonato.exercicio.jogo.request;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class UpdatePartida{
    Long id;
    @Min(0)
    Integer golsMand;
    @Min(0)
    Integer golsVisit;
}
