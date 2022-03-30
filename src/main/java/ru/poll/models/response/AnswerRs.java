package ru.poll.models.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AnswerRs {

    Long id;
    String body;
}
