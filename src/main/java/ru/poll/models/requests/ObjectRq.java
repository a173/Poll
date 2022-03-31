package ru.poll.models.requests;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotNull;

@Value
@Builder
@Jacksonized
public class ObjectRq {
    @NotNull
    Object content;
}
