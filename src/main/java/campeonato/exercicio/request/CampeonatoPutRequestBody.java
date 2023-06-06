package campeonato.exercicio.request;

import lombok.Data;
@Data
public class CampeonatoPutRequestBody {
    Integer id;
    String nome;
    Integer ano;
    Integer timeMand;
    Integer timeVisit;
    Boolean status;  //  I-iniciado   F-finalizado
}