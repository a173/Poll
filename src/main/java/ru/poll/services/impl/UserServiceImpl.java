package ru.poll.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.poll.constants.ExceptionMessage;
import ru.poll.constants.LogMessage;
import ru.poll.exceptions.NotFoundException;
import ru.poll.exceptions.ValidationException;
import ru.poll.mappers.UserMapper;
import ru.poll.models.dto.QuestionTypeDto;
import ru.poll.models.entity.Answer;
import ru.poll.models.entity.Poll;
import ru.poll.models.entity.Question;
import ru.poll.models.entity.User;
import ru.poll.models.request.AnswerObjectRq;
import ru.poll.models.response.PollRs;
import ru.poll.models.response.PollShortRs;
import ru.poll.models.response.UserRs;
import ru.poll.repository.UserRepository;
import ru.poll.services.AnswerService;
import ru.poll.services.PollService;
import ru.poll.services.QuestionService;
import ru.poll.services.UserService;
import ru.poll.utils.Validate;

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
    private final AnswerService answerService;
    private final Validate validate;
    private final ObjectMapper objectMapper;

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
    @Transactional
    public void beginPoll(User user, Long pollId, Long questionId, AnswerObjectRq answer) throws NotFoundException, ValidationException {
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
        if (user.getAnswers().stream()
                .map(Answer::getQuestion).collect(Collectors.toSet()).contains(question))
            throw new ValidationException(ExceptionMessage.ALREADY_ANSWER);

        if (question.getType().equals(QuestionTypeDto.SINGLE))
            user.getAnswers().add(answerService.getAnswer(question, objectMapper.convertValue(answer.getContent(), Long.class)));
        else if (question.getType().equals(QuestionTypeDto.MULTIPLE))
            for (Long answerId : objectMapper.convertValue(answer.getContent(), Long[].class))
                user.getAnswers().add(answerService.getAnswer(question, answerId));
        else
            user.getAnswers().add(answerService
                    .save(objectMapper.convertValue(answer.getContent(), String.class), question));
        userRepository.save(user);
    }
}
