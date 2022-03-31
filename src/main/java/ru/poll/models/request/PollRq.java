package ru.poll.models.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import ru.poll.models.interfaces.IPoll;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Value
@Builder
@Jacksonized
public class PollRq implements IPoll {
    @NotNull
    String title;
    @JsonFormat(pattern = "yyy-MM-dd'T'HH:mm:ss.SSSX")
    Date dateFrom;
    @JsonFormat(pattern = "yyy-MM-dd'T'HH:mm:ss.SSSX")
    Date dateTo;
    String description;
    @Valid
    Set<QuestionRq> questions;
}
