package campeonato.exercicio.time.service;

import campeonato.exercicio.time.domain.Time;
import campeonato.exercicio.time.repository.TimeRepository;
import campeonato.exercicio.time.request.TimePostRequestBody;
import campeonato.exercicio.time.request.TimePutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

//regras de negócio
@Service
@RequiredArgsConstructor
public class TimeService {
    private final TimeRepository timeRepository;
    public TimeRepository getTimeRepository() {
        return timeRepository;
    }

    public List<Time> listAll() {
        return getTimeRepository().findAll();
    }
    public Time findByIdOrThrowBackBadRequestException(Long id) {
        return getTimeRepository().findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Time não encontrado"));
    }
    public void findByNomeOrThrowBackBadRequestException(TimePostRequestBody timePostRequestBody) {
        if (timeRepository.existsByNome(timePostRequestBody.getNome())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Time "
                    +timePostRequestBody.getNome()+" já cadastrado");
        }
    }
    public void delete(Long id){
        getTimeRepository().delete(findByIdOrThrowBackBadRequestException(id));
    }

    public void replace(TimePutRequestBody timePutRequestBody) {
        Time timeSalvo = findByIdOrThrowBackBadRequestException(timePutRequestBody.getId());
        Time time = Time.builder()
                .id(timeSalvo.getId())
                .nome(timePutRequestBody.getNome())
                .build();
        timeRepository.save(time);
    }
    public Time save(TimePostRequestBody timePostRequestBody) {
        findByNomeOrThrowBackBadRequestException(timePostRequestBody);
        return timeRepository.save(Time.builder().nome(timePostRequestBody.getNome()).build());
    }
}
