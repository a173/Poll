package ru.poll.models.dto;

import lombok.Builder;
import lombok.Value;
import ru.poll.models.interfaces.AnswerInterface;

@Value
@Builder
public class AnswerDto implements AnswerInterface {
    Long id;
    String body;
}
