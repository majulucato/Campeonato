package campeonato.exercicio.campeonato.service;

import campeonato.exercicio.campeonato.domain.Campeonato;
import campeonato.exercicio.campeonato.dto.CampeonatoDTO;
import campeonato.exercicio.campeonato.repository.CampeonatosRepository;
import campeonato.exercicio.campeonato.request.CampeonatoPostRequestBody;
import campeonato.exercicio.campeonato.request.CampeonatoPutRequestBody;
import campeonato.exercicio.pontuacao.domain.Pontuacao;
import campeonato.exercicio.pontuacao.repository.PontuacaoRepository;
import campeonato.exercicio.time.domain.Time;
import campeonato.exercicio.time.service.TimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CampeonatoService {
    private final CampeonatosRepository campeonatosRepository;
    private final TimeService timeService;
    private final PontuacaoRepository pontuacaoRepository;

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
    @Transactional
    public Campeonato save(@Validated CampeonatoPostRequestBody campeonatoPostRequestBody) {
        sameNomeAno(campeonatoPostRequestBody);
        campeonatoPostRequestBody.setIniciado(false);
        campeonatoPostRequestBody.setFinalizado(false);
        return getCampeonatosRepository().save(Campeonato.builder().nome(campeonatoPostRequestBody.getNome())
                .ano(campeonatoPostRequestBody.getAno()).iniciado(campeonatoPostRequestBody.getIniciado())
                .finalizado(campeonatoPostRequestBody.getFinalizado()).build());
    }
    @Transactional
    public void delete(Long id) {
        getCampeonatosRepository().delete(findByIdOrThrowBackBadRequestException(id));
    }
    @Transactional
    public void replace(CampeonatoPutRequestBody campeonatoPutRequestBody) {
        Campeonato campSalvo = findByIdOrThrowBackBadRequestException(campeonatoPutRequestBody.getId());
        sameNomeAnoPut(campeonatoPutRequestBody);
        validateFinalizadoOuNaoIniciado(campSalvo.getCampeonatoId());
        Campeonato campeonatoNew = Campeonato.builder().campeonatoId(campSalvo.getCampeonatoId()).nome(campeonatoPutRequestBody.getNome())
                .ano(campeonatoPutRequestBody.getAno()).iniciado(campSalvo.getIniciado())
                .finalizado(campSalvo.getFinalizado()).build();
        getCampeonatosRepository().save(campeonatoNew);
    }
    public void sameNomeAno(CampeonatoPostRequestBody campeonatoPostRequestBody) {
        if (campeonatosRepository.existsByNomeAndAno(campeonatoPostRequestBody.getNome(), campeonatoPostRequestBody.getAno())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Campeonato já cadastrado no ano "+ campeonatoPostRequestBody.getAno()+"\n");
        }
    }
    public void sameNomeAnoPut(CampeonatoPutRequestBody campeonatoPutRequestBody) {
        if (campeonatosRepository.existsByNomeAndAno(campeonatoPutRequestBody.getNome(), campeonatoPutRequestBody.getAno())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não é possível atualizar o campeonato; Campeonato já existente");
        }
    }
    public void sameTime(CampeonatoDTO campeonatoDTO){
        Set<Long> set = new HashSet<>();
        campeonatoDTO.getTime().forEach(time -> { if(!set.add(time)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O time "+time+ " já está cadastrado neste campeonato");
            }
        });
    }
    @Transactional
    public void startCampeonato(CampeonatoDTO campeonatoDTO) {
        validateStartCampeonato(campeonatoDTO);
        Campeonato campeonatoSalvo = findByIdOrThrowBackBadRequestException(campeonatoDTO.getCampeonatoId());
        campeonatoSalvo.setIniciado(true);
        campeonatoSalvo.setFinalizado(false);
        campeonatosRepository.save(campeonatoSalvo);
        addTimeAoCampeonato(campeonatoSalvo,campeonatoDTO);
    }
    public void validateStartCampeonato(CampeonatoDTO campeonatoDTO){
        validateIniciadoOuFinalizado(campeonatoDTO);
        validateMinTimes(campeonatoDTO);
        sameTime(campeonatoDTO);
    }
    public void finishCampeonato(Long id) {
        //validar se todos ja jogaram entre si 2x
        //validar se o campeonato estava iniciado (validateFinalizadoOuNaoIniciado)
        Campeonato campeonatoSalvo = findByIdOrThrowBackBadRequestException(id);
        campeonatoSalvo.setIniciado(false);
        campeonatoSalvo.setFinalizado(true);
        campeonatosRepository.save(campeonatoSalvo);
    }
    public void validateFinishCampeonato(Long id){
        validateFinalizadoOuNaoIniciado(id);
        //validar status do campeonato
    }

    public void validateFinalizadoOuNaoIniciado(long id) {
        if (campeonatosRepository.statusFinalizado(id) || (!campeonatosRepository.statusIniciado(id) &&
                !campeonatosRepository.statusFinalizado(id))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Campeonato já finalizado ou não inicializado");
        }
    }
    public void validateIniciadoOuFinalizado(CampeonatoDTO campeonatoDTO) {
        if (campeonatosRepository.statusIniciado(campeonatoDTO.getCampeonatoId()) ||
                campeonatosRepository.statusFinalizado(campeonatoDTO.getCampeonatoId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Campeonato já iniciado ou finalizado");
        }
    }
    public void validateMinTimes(CampeonatoDTO campeonatoDTO){
        if (campeonatoDTO.getTime().size() < 4){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Quantidade Mínima de participantes para o campeonato não atingida");
        }
    }
    @Transactional
    public void addTimeAoCampeonato(Campeonato campeonato, CampeonatoDTO campeonatoDTO){
        for (int i = 0; i < campeonatoDTO.getTime().size(); i++) {
            Pontuacao pontuacao = createPontuacao(campeonato, timeService
                    .findByIdOrThrowBackBadRequestException(campeonatoDTO.getTime().get(i)));
            pontuacaoRepository.save(pontuacao);
        }
    }
     private Pontuacao createPontuacao(Campeonato campeonato, Time time) {
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