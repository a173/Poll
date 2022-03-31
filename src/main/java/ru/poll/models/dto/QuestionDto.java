package ru.poll.models.dto;

import lombok.Builder;
import lombok.Value;
import ru.poll.models.interfaces.IQuestion;

import java.util.Set;

@Value
@Builder
public class QuestionDto implements IQuestion {

    Long id;
    String body;
    QuestionTypeDto type;
    Set<AnswerDto> answers;
}
