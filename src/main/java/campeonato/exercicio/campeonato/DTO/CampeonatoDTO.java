package campeonato.exercicio.campeonato.DTO;

import lombok.Data;

import java.util.List;

@Data
public class CampeonatoDTO {
    private Boolean tipoPartida; //0-Amistoso 1-Oficial*/
    //partida Amistoso e campeonato poderia ser boolean ai quando partida for 1(oficial) contar os pontos

    private List<Integer> times;
}
