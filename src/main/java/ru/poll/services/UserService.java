package ru.poll.services;

import ru.poll.exceptions.NotFoundException;
import ru.poll.exceptions.ValidationException;
import ru.poll.models.entities.User;
import ru.poll.models.requests.ObjectRq;
import ru.poll.models.responses.PollRs;
import ru.poll.models.responses.PollShortRs;
import ru.poll.models.responses.UserRs;

import java.util.Set;

public interface UserService {

    UserRs subscribePoll(Long pollId, User user) throws NotFoundException, ValidationException;

    UserRs unsubscribePoll(Long pollId, User user) throws NotFoundException, ValidationException;

    Set<PollShortRs> mySubscribe(User user) throws NotFoundException;

    Set<PollRs> getStartedPolls(User user) throws NotFoundException;

    Set<PollRs> getPollsUser(Long userId) throws NotFoundException;

    void beginPoll(User user, Long pollId, Long questionId, ObjectRq answer) throws NotFoundException, ValidationException;
}
