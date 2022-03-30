package ru.poll.mappers;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.poll.models.dto.PollDto;
import ru.poll.models.entity.Poll;
import ru.poll.models.request.PollRq;
import ru.poll.models.response.PollRs;
import ru.poll.models.response.PollShortRs;

import java.util.Objects;
import java.util.Set;

@Mapper(uses = {QuestionMapper.class})
public interface PollMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", ignore = true)
    Poll toEntity(PollRq poll);

    @Mapping(target = "users", ignore = true)
    Poll toEntity(PollDto poll);

    @AfterMapping
    default void addEntity(@MappingTarget Poll poll) {
        if (Objects.nonNull(poll.getQuestions()))
            poll.getQuestions().forEach(q -> q.setPoll(poll));
    }

    PollRs toRs(Poll poll);

    Set<PollRs> toRs(Set<Poll> poll);

    PollShortRs toShortRs(Poll poll);

    Set<PollShortRs> toShortRs(Set<Poll> poll);

    PollDto toDto(Poll poll);
}
