package ru.poll.models.response;

import lombok.Builder;
import lombok.Value;
import ru.poll.models.dto.QuestionTypeDto;

import java.util.Set;

@Value
@Builder
public class QuestionRs {

    Long id;
    String body;
    QuestionTypeDto type;
    Set<AnswerRs> answers;
}
