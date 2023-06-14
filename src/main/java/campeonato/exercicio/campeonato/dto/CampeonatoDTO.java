package campeonato.exercicio.campeonato.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class CampeonatoDTO {
    private Long campeonatoId;
    @NotEmpty
    private List<Long> time;
}
