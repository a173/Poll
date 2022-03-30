package ru.poll.models.response;

import lombok.Value;

import java.util.Set;

@Value
public class UserRs {

    Long id;
    String username;
    Set<PollShortRs> polls;
}
