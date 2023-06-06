package campeonato.exercicio.request;

import lombok.Data;

@Data
public class JogosPutRequestBody {
    Integer id;
    String nomePart;// time_mand x time_visit
    Boolean tipo;//0-Amistoso 1-Oficial
    String timeMandante;
    String timeVisitante;
    Boolean status;//1-iniciado   0-finalizado*/
}
