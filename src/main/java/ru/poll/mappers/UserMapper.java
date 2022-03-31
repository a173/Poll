package ru.poll.mappers;

import org.mapstruct.Mapper;
import ru.poll.models.entities.User;
import ru.poll.models.responses.UserRs;

@Mapper(uses = {PollMapper.class})
public interface UserMapper {

    UserRs toRs(User user);
}
