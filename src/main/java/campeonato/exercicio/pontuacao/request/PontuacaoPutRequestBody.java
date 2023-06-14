package campeonato.exercicio.pontuacao.request;

import campeonato.exercicio.campeonato.domain.Campeonato;
import campeonato.exercicio.time.domain.Time;
import lombok.Data;

@Data
public class PontuacaoPutRequestBody {
    private Long id;
    private Integer pont;
    private Integer quantJogos;
    private Integer quantVitorias;
    private Integer quantDerrotas;
    private Integer quantEmpates;
    private Integer quantGolsFeitos;
    private Integer quantGolsSofridos;
    private Campeonato campeonato;
}
