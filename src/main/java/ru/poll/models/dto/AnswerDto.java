package ru.poll.models.dto;

import lombok.Builder;
import lombok.Value;
import ru.poll.models.interfaces.IAnswer;

@Value
@Builder
public class AnswerDto implements IAnswer {
    Long id;
    String body;
}
