package ru.poll.mappers;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.poll.models.dto.QuestionDto;
import ru.poll.models.entities.Poll;
import ru.poll.models.entities.Question;
import ru.poll.models.requests.QuestionRq;
import ru.poll.models.responses.QuestionRs;

import java.util.Objects;

@Mapper(uses = {AnswerMapper.class})
public interface QuestionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "poll", ignore = true)
    Question toEntity(QuestionRq question);

    @Mapping(target = "id", ignore = true)
    Question toEntity(QuestionRq question, Poll poll);

    @Mapping(target = "poll", ignore = true)
    Question toEntity(QuestionDto question);

    @AfterMapping
    default void addEntity(@MappingTarget Question question) {
        if (Objects.nonNull(question.getAnswers()))
            question.getAnswers().forEach(a -> a.setQuestion(question));
    }

    QuestionRs toRs(Question question);

    QuestionDto toDto(Question question);
}
