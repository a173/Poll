package ru.poll.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.poll.models.dto.AnswerDto;
import ru.poll.models.entities.Answer;
import ru.poll.models.entities.Question;
import ru.poll.models.requests.AnswerRq;
import ru.poll.models.responses.AnswerRs;

@Mapper
public interface AnswerMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "question", ignore = true)
    @Mapping(target = "users", ignore = true)
    Answer toEntity(AnswerRq answer);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "body", source = "answer.body")
    Answer toEntity(AnswerRq answer, Question question);

    @Mapping(target = "question", ignore = true)
    @Mapping(target = "users", ignore = true)
    Answer toEntity(AnswerDto answer);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "body", source = "body")
    @Mapping(target = "question", source = "question")
    Answer toEntity(String body, Question question);

    AnswerRs toRs(Answer answer);

    AnswerDto toDto(Answer answer);
}
