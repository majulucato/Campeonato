package campeonato.exercicio.campeonato.request;

import campeonato.exercicio.campeonato.dto.CampeonatoDTO;
import lombok.Data;

@Data
public class CampeonatoPostRequestBody {
    private Long id;
    private String nome;
    private Integer ano;
    private CampeonatoDTO campeonatoDTO;
}
