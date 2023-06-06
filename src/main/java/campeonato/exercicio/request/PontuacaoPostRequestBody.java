package campeonato.exercicio.request;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class PontuacaoPostRequestBody {
    private Integer id;
    private String nomeTime;
    private Integer pont;
    private Integer quantVitorias;
    private Integer quantDerrotas;
    private Integer quantGolsFeitos;
    private Integer quantGolsSofridos;
}