package ru.poll.models.dto;

import lombok.Builder;
import lombok.Value;
import ru.poll.models.interfaces.IPoll;

import java.util.Date;
import java.util.Set;

@Value
@Builder
public class PollDto implements IPoll {

    Long id;
    String title;
    Date dateFrom;
    Date dateTo;
    String description;
    Set<QuestionDto> questions;
}
