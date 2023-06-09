package campeonato.exercicio.campeonato.service;

import campeonato.exercicio.campeonato.domain.Campeonato;
import campeonato.exercicio.campeonato.dto.CampeonatoDTO;
import campeonato.exercicio.campeonato.repository.CampeonatosRepository;
import campeonato.exercicio.campeonato.request.CampeonatoPostRequestBody;
import campeonato.exercicio.campeonato.request.CampeonatoPutRequestBody;
import campeonato.exercicio.pontuacao.domain.Pontuacao;
import campeonato.exercicio.time.domain.Time;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service

public class CampeonatoService {
    private final CampeonatosRepository campeonatosRepository;

    public CampeonatoService (CampeonatosRepository campeonatosRepository){
        this.campeonatosRepository=campeonatosRepository;
    }



    public CampeonatosRepository getCampeonatosRepository() {
        return campeonatosRepository;
    }

    @Transactional(readOnly = true)
    public Campeonato findByIdOrThrowBackBadRequestException(Long id) {
        return getCampeonatosRepository().findById(Math.toIntExact(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Campeonato não encontrado"));
    }

    @Transactional(readOnly = true)
    public Page<Campeonato> listAll(Pageable pageable) {
        return getCampeonatosRepository().findAll(pageable);
    }

    public Campeonato save(CampeonatoPostRequestBody campeonatoPostRequestBody) {
        sameNomeAno(campeonatoPostRequestBody);
        return getCampeonatosRepository().save(Campeonato.builder().nome(campeonatoPostRequestBody.getNome())
                .ano(campeonatoPostRequestBody.getAno()).build());
    }

    public void delete(Long id) {
        getCampeonatosRepository().delete(findByIdOrThrowBackBadRequestException(id));
    }

    public void replace(CampeonatoPutRequestBody campeonatoPutRequestBody) {
        Campeonato campSalvo = findByIdOrThrowBackBadRequestException(campeonatoPutRequestBody.getId());
        validateFinalizadoOuNaoIniciado(campeonatoPutRequestBody.getId());
        Campeonato campeonato = Campeonato.builder().id(campSalvo.getId()).nome(campeonatoPutRequestBody.getNome())
                .ano(campeonatoPutRequestBody.getAno()).build();
        getCampeonatosRepository().save(campeonato);
    }

    public void sameNomeAno(CampeonatoPostRequestBody campeonatoPostRequestBody) {
        if (getCampeonatosRepository().existsByNomeAndAno(campeonatoPostRequestBody.getNome(), campeonatoPostRequestBody.getAno())){
            new ResponseStatusException(HttpStatus.BAD_REQUEST, "Campeonato já cadastrado no ano"+ campeonatoPostRequestBody.getAno()+"\n");
        }
    }

   /* public void sameTime(CampeonatoPostRequestBody campeonatoPostRequestBody) {
        if (){
            new ResponseStatusException(HttpStatus.BAD_REQUEST, "Time já cadastrado no campeonato:"+ campeonatoPostRequestBody.getTime()+"\n");
        }
    }*/

    public void statusIniciado(CampeonatoDTO campeonatoDTO){
        if (campeonatosRepository.statusIniciado(campeonatoDTO.getCampeonatoId()) || campeonatosRepository.statusFinalizado(campeonatoDTO.getCampeonatoId())){
            new ResponseStatusException(HttpStatus.BAD_REQUEST, "Campeonato já iniciado\n");
        }
    }
    public void statusFinalizado(CampeonatoDTO campeonatoDTO){
        if (campeonatosRepository.statusFinalizado(campeonatoDTO.getCampeonatoId())|| campeonatosRepository.statusIniciado(campeonatoDTO.getCampeonatoId())){
            new ResponseStatusException(HttpStatus.BAD_REQUEST, "Campeonato finalizado\n");
        }
    }

    public void statusCampeonato(CampeonatoDTO campeonatoDTO){
        if (campeonatosRepository.statusIniciado(campeonatoDTO.getCampeonatoId()) || campeonatosRepository.statusFinalizado(campeonatoDTO.getCampeonatoId())
        && campeonatosRepository.statusIniciado(campeonatoDTO.getCampeonatoId())){
            new ResponseStatusException(HttpStatus.BAD_REQUEST, "Campeonato já iniciado\n");
        }else{
            new ResponseStatusException(HttpStatus.BAD_REQUEST, "Campeonato já finalizado\n");
        }
    }
    public void validateFinalizadoOuNaoIniciado(long id) {
        if (campeonatosRepository.statusFinalizado(id) || (!campeonatosRepository.statusIniciado(id) &&
                !campeonatosRepository.statusFinalizado(id))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Campeonato já finalizado ou não inicializado");
        }
    }
    public void validateStatusCampeonato(CampeonatoDTO campeonatoDTO) {
        if (campeonatosRepository.statusIniciado(campeonatoDTO.getCampeonatoId()) ||
                campeonatosRepository.statusFinalizado(campeonatoDTO.getCampeonatoId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Campeonato já iniciado ou finalizado");
        }
    }
     private Pontuacao createPontuacao(Campeonato campeonato, Time time, CampeonatoDTO campeonatoDTO) {
         Pontuacao pontuacao = new Pontuacao();
         pontuacao.setCampeonato(campeonato);
         pontuacao.setNomeTime(time);
         pontuacao.setPont(0);
         pontuacao.setQuantJogos(0);
         pontuacao.setQuantVitorias(0);
         pontuacao.setQuantEmpates(0);
         pontuacao.setQuantDerrotas(0);
         pontuacao.setQuantGolsFeitos(0);
         pontuacao.setQuantGolsSofridos(0);
         return pontuacao;
     }
}