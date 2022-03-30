package ru.poll.mappers;

import org.mapstruct.Mapper;
import ru.poll.models.entity.User;
import ru.poll.models.response.UserRs;

@Mapper(uses = {PollMapper.class})
public interface UserMapper {

    UserRs toRs(User user);
}
