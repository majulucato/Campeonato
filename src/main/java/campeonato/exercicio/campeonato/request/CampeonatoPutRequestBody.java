package campeonato.exercicio.campeonato.request;

import lombok.Data;
@Data
public class CampeonatoPutRequestBody {
    Long id;
    String nome;
    Integer ano;
    Integer timeMand;
    Integer timeVisit;
    Boolean status;  //  I-iniciado   F-finalizado
}