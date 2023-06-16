package campeonato.exercicio.time.service;

import campeonato.exercicio.time.domain.Time;
import campeonato.exercicio.time.repository.TimeRepository;
import campeonato.exercicio.time.request.TimePostRequestBody;
import campeonato.exercicio.time.request.TimePutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

//regras de negócio
@Service
@RequiredArgsConstructor
public class TimeService {
    private final TimeRepository timeRepository;
    public TimeRepository getTimeRepository() {
        return timeRepository;
    }

    public Page<Time> listAll(Pageable pageable) {
        return getTimeRepository().findAll(pageable);
    }
    public Time findByIdOrThrowBackBadRequestException(Long id) {
        return getTimeRepository().findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Time não encontrado"));
    }
    public void findByNomeOrThrowBackBadRequestException(String nome) {
        if (timeRepository.existsByNome(nome)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Time "
                    +nome+" já cadastrado");
        }
    }
    public void delete(Long id){
        getTimeRepository().delete(findByIdOrThrowBackBadRequestException(id));
    }

    public void replace(TimePutRequestBody timePutRequestBody) {
        Time timeSalvo = findByIdOrThrowBackBadRequestException(timePutRequestBody.getId());
        findByNomeOrThrowBackBadRequestException(timePutRequestBody.getNome());
        Time time = Time.builder()
                .timeId(timeSalvo.getTimeId())
                .nome(timePutRequestBody.getNome())
                .build();

        timeRepository.save(time);
    }
    public Time save(TimePostRequestBody timePostRequestBody) {
        findByNomeOrThrowBackBadRequestException(timePostRequestBody.getNome());
        return timeRepository.save(Time.builder().nome(timePostRequestBody.getNome()).build());
    }
}
