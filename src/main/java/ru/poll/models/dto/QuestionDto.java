package ru.poll.models.dto;

import lombok.Builder;
import lombok.Value;
import ru.poll.models.interfaces.QuestionInterface;

import java.util.Set;

@Value
@Builder
public class QuestionDto implements QuestionInterface {

    Long id;
    String body;
    QuestionTypeDto type;
    Set<AnswerDto> answers;
}
