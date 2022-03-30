package ru.poll.models.request;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import ru.poll.models.interfaces.AnswerInterface;

import javax.validation.constraints.NotNull;

@Value
@Builder
@Jacksonized
public class AnswerRq implements AnswerInterface {
    @NotNull
    String body;
}
