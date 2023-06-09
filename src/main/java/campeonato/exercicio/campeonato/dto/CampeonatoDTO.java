package campeonato.exercicio.campeonato.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class CampeonatoDTO {
    private Long campeonatoId;
    private List<Long> time;
}
