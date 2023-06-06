package campeonato.exercicio.service;

import campeonato.exercicio.domain.Times;
import campeonato.exercicio.repository.TimesRepository;
import campeonato.exercicio.request.TimesPostRequestBody;
import campeonato.exercicio.request.TimesPutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

//regras de negócio
@Service
@RequiredArgsConstructor
public class TimesService {
    private final TimesRepository timesRepository;
    public TimesRepository getTimesRepository() {
        return timesRepository;
    }

    public List<Times> listAll() {
        return getTimesRepository().findAll();
    }
    public Times findByIdOrThrowBackBadRequestException(Integer id) {
        return getTimesRepository().findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Time não encontrado"));
    }
    public void delete(Integer id){
        getTimesRepository().delete(findByIdOrThrowBackBadRequestException(id));
    }

    public void replace(TimesPutRequestBody timesPutRequestBody) {
        Times timeSalvo = findByIdOrThrowBackBadRequestException(timesPutRequestBody.getId());
        Times times = Times.builder()
                .id(timeSalvo.getId())
                .nome(timesPutRequestBody.getNome())
                .build();
        timesRepository.save(times);
    }
    public Times save(TimesPostRequestBody timesPostRequestBody) {
        return timesRepository.save(Times.builder().nome(timesPostRequestBody.getNome()).build());
    }

}
