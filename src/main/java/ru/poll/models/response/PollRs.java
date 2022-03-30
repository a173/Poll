package ru.poll.models.response;

import lombok.Value;

import java.util.Date;
import java.util.Set;

@Value
public class PollRs {

    Long id;
    String title;
    Date dateFrom;
    Date dateTo;
    Set<QuestionRs> questions;
}
