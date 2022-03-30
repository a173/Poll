package ru.poll.services;

import ru.poll.exceptions.NotFoundException;
import ru.poll.exceptions.ValidationException;
import ru.poll.models.entity.User;
import ru.poll.models.request.AnswerObjectRq;
import ru.poll.models.response.PollRs;
import ru.poll.models.response.PollShortRs;
import ru.poll.models.response.UserRs;

import java.util.Set;

public interface UserService {

    UserRs subscribePoll(Long pollId, User user) throws NotFoundException, ValidationException;

    UserRs unsubscribePoll(Long pollId, User user) throws NotFoundException, ValidationException;

    Set<PollShortRs> mySubscribe(User user) throws NotFoundException;

    Set<PollRs> getStartedPolls(User user) throws NotFoundException;

    void beginPoll(User user, Long pollId, Long questionId, AnswerObjectRq answer) throws NotFoundException, ValidationException;
}
