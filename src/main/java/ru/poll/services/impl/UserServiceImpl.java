package ru.poll.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.poll.constants.ExceptionMessage;
import ru.poll.constants.LogMessage;
import ru.poll.exceptions.NotFoundException;
import ru.poll.exceptions.ValidationException;
import ru.poll.mappers.UserMapper;
import ru.poll.models.entities.Answer;
import ru.poll.models.entities.Poll;
import ru.poll.models.entities.Question;
import ru.poll.models.entities.User;
import ru.poll.models.requests.ObjectRq;
import ru.poll.models.responses.PollRs;
import ru.poll.models.responses.PollShortRs;
import ru.poll.models.responses.UserRs;
import ru.poll.repository.UserRepository;
import ru.poll.services.PollService;
import ru.poll.services.QuestionService;
import ru.poll.services.UserService;
import ru.poll.validates.Validate;
import ru.poll.converters.AnswerHandler;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PollService pollService;
    private final QuestionService questionService;
    private final Validate validate;
    @Resource
    private final Map<String, AnswerHandler> answerMap;

    @Override
    @Transactional
    public UserRs subscribePoll(Long pollId, User user) throws NotFoundException, ValidationException {
        log.info(LogMessage.SUBSCRIBE, user.getId(), pollId);

        Poll poll = pollService.getPoll(pollId);
        if (validate.isAfterNow(poll.getDateTo()))
            throw new ValidationException(ExceptionMessage.POLL_FINISHED);
        if (user.getPolls().contains(poll))
            throw new ValidationException(ExceptionMessage.ALREADY_SUBSCRIBE);
        user.getPolls().add(poll);
        userRepository.save(user);

        return userMapper.toRs(user);
    }

    @Override
    @Transactional
    public UserRs unsubscribePoll(Long pollId, User user) throws NotFoundException, ValidationException {
        log.info(LogMessage.UNSUBSCRIBE, user.getId(), pollId);

        Poll poll = pollService.getPoll(pollId);
        if (!user.getPolls().contains(poll))
            throw new ValidationException(ExceptionMessage.NOT_SUBSCRIBE);
        user.getPolls().remove(poll);
        userRepository.save(user);

        return userMapper.toRs(user);
    }

    @Override
    public Set<PollShortRs> mySubscribe(User user) throws NotFoundException {
        log.info(LogMessage.GET_SUBSCRIPTIONS, user.getId());

        return pollService.getPollsShortRs(user.getPolls());
    }

    @Override
    public Set<PollRs> getStartedPolls(User user) throws NotFoundException {
        log.info(LogMessage.GET_SUBSCRIPTIONS_DETAILS, user.getId());

        return pollService.getPollsRs(user);
    }

    @Override
    public Set<PollRs> getPollsUser(Long userId) throws NotFoundException {
        log.info(LogMessage.GET_SUBSCRIPTIONS_DETAILS, userId);

        return pollService.getPollsRs(userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(ExceptionMessage.USERID_NOT_FOUND, userId))));
    }

    @Override
    @Transactional
    public void beginPoll(User user, Long pollId, Long questionId, ObjectRq answer) throws NotFoundException, ValidationException {
        log.info(LogMessage.USER_ANSWER, user.getId(), questionId);

        Poll poll = pollService.getPoll(pollId);
        Question question = questionService.getQuestion(questionId);
        if (!validate.isAfterNow(poll.getDateFrom()))
            throw new ValidationException(ExceptionMessage.POLL_NOT_STARTED);
        if (validate.isAfterNow(poll.getDateTo()))
            throw new ValidationException(ExceptionMessage.POLL_FINISHED);
        if (!poll.getUsers().contains(user))
            throw new ValidationException(ExceptionMessage.NOT_SUBSCRIBE);
        if (!poll.getQuestions().contains(question))
            throw new ValidationException(ExceptionMessage.QUESTION_NOT_RELATIONSHIP_TO_POLL);
        if (user.getAnswers().stream().map(Answer::getQuestion).collect(Collectors.toSet()).contains(question))
            throw new ValidationException(ExceptionMessage.ALREADY_ANSWER);

        Optional.ofNullable(answerMap.get(question.getType().name()))
                .orElseThrow(RuntimeException::new)
                .handle(answer.getContent(), user.getAnswers(), question);
        userRepository.save(user);
    }
}
