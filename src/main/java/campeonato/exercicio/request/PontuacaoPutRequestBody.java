package campeonato.exercicio.request;

import lombok.Data;

@Data
public class PontuacaoPutRequestBody {
    private Integer id;
    private String nomeTime;
    private Integer pont;
    private Integer quantVitorias;
    private Integer quantDerrotas;
    private Integer quantGolsFeitos;
    private Integer quantGolsSofridos;
}
