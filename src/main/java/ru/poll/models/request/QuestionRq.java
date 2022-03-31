package ru.poll.models.request;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import ru.poll.models.dto.QuestionTypeDto;
import ru.poll.models.interfaces.IQuestion;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Value
@Builder
@Jacksonized
public class QuestionRq implements IQuestion {
    @NotNull
    String body;
    @NotNull
    QuestionTypeDto type;
    @Valid
    Set<AnswerRq> answers;
}
