package campeonato.exercicio.campeonato.request;

import campeonato.exercicio.campeonato.dto.CampeonatoDTO;
import lombok.Data;
@Data
public class CampeonatoPutRequestBody {
    private Long id;
    private String nome;
    private Integer ano;
    private Boolean iniciado;
    private Boolean finalizado;
    private CampeonatoDTO campeonatoDTO;
}