package ru.poll.models.request;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import ru.poll.models.interfaces.IAnswer;

import javax.validation.constraints.NotNull;

@Value
@Builder
@Jacksonized
public class AnswerRq implements IAnswer {
    @NotNull
    String body;
}
