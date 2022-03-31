package ru.poll.models.responses;

import lombok.Value;

import java.util.Date;

@Value
public class PollShortRs {

    Long id;
    String title;
    Date dateFrom;
    Date dateTo;
}
