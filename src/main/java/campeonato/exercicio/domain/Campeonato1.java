package campeonato.exercicio.domain;

import lombok.Data;

import java.util.List;

@Data
public class Campeonato1 {
    private boolean tipoPartida; //0-Amistoso 1-Oficial*/
    //partida Amistoso e campeonato poderia ser boolean ai quando partida for 1(oficial) contar os pontos

    private List<Campeonatos> times;
}
