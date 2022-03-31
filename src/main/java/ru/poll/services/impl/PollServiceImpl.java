package ru.poll.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.poll.constants.ExceptionMessage;
import ru.poll.constants.LogMessage;
import ru.poll.exceptions.DeleteException;
import ru.poll.exceptions.NotFoundException;
import ru.poll.exceptions.UpdateException;
import ru.poll.exceptions.ValidationException;
import ru.poll.mappers.PollMapper;
import ru.poll.models.dto.PollDto;
import ru.poll.models.dto.QuestionTypeDto;
import ru.poll.models.entities.Poll;
import ru.poll.models.entities.User;
import ru.poll.models.requests.PollRq;
import ru.poll.models.requests.QuestionRq;
import ru.poll.models.responses.PollRs;
import ru.poll.models.responses.PollShortRs;
import ru.poll.repository.PollRepository;
import ru.poll.services.PollService;
import ru.poll.services.QuestionService;
import ru.poll.validates.Validate;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Log4j2
@Service
@RequiredArgsConstructor
public class PollServiceImpl implements PollService {

    private final PollMapper pollMapper;
    private final PollRepository pollRepository;
    private final ObjectMapper objectMapper;
    private final Validate validate;
    private final QuestionService questionService;

    @Override
    @Transactional
    public PollRs savePoll(PollRq pollRq) throws ValidationException {
        log.info(LogMessage.CREATE_POLL, pollRq);

        return pollMapper.toRs(pollRepository.save(
                pollMapper.toEntity(validate.poll(pollRq))
        ));
    }

    @Override
    @Transactional
    public PollRs updatePoll(Long id, JsonPatch jsonPatch)
            throws NotFoundException, JsonPatchException, JsonProcessingException, ValidationException, UpdateException {
        log.info(LogMessage.UPDATE_POLL, id);

        Poll poll = getPoll(id);
        if (validate.isAfterNow(poll.getDateFrom()))
            throw new UpdateException(ExceptionMessage.POLL_ALREADY_STARTED_UPDATE_DENIED);
        JsonNode patched = jsonPatch.apply(objectMapper.convertValue(pollMapper.toDto(poll), JsonNode.class));
        PollDto pollPatched = objectMapper.treeToValue(patched, PollDto.class);

        return pollMapper.toRs(pollRepository.save(
                pollMapper.toEntity(validate.poll(pollPatched))
        ));
    }

    @Override
    public void deletePoll(Long id) throws NotFoundException, DeleteException {
        log.info(LogMessage.DELETE_POLL, id);

        Poll poll = getPoll(id);
        if (validate.isAfterNow(poll.getDateFrom()))
            throw new DeleteException(ExceptionMessage.POLL_ALREADY_STARTED_DELETE_DENIED);

        pollRepository.delete(poll);
    }

    @Override
    @Transactional
    public PollRs addQuestions(Long id, Set<QuestionRq> questions)
            throws NotFoundException, UpdateException, ValidationException {
        log.info(LogMessage.ADD_QUESTIONS_TO_POLL, id);

        Poll poll = getPoll(id);
        if (validate.isAfterNow(poll.getDateFrom()))
            throw new UpdateException(ExceptionMessage.POLL_ALREADY_STARTED_UPDATE_DENIED);

        return pollMapper.toRs(pollRepository.save(questionService.addPollToQuestions(poll, questions)));
    }

    @Override
    public Poll getPoll(Long id) throws NotFoundException {
        return pollRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionMessage.POLL_NOT_FOUND, id)));
    }

    @Override
    public Set<PollShortRs> getPollsShortRs(Set<Poll> polls) throws NotFoundException {
        if (Objects.isNull(polls) || polls.isEmpty())
            throw new NotFoundException(ExceptionMessage.POLLS_NOT_FOUND);
        return pollMapper.toShortRs(polls);
    }

    @Override
    public Set<PollRs> getPollsRs(User user) throws NotFoundException {
        if (Objects.isNull(user.getPolls()) || user.getPolls().isEmpty())
            throw new NotFoundException(ExceptionMessage.POLLS_NOT_FOUND);
        user.getPolls().removeIf(p -> !validate.isAfterNow(p.getDateFrom()));
        user.getPolls().forEach(p -> p.getQuestions().forEach(q -> q.getAnswers().retainAll(user.getAnswers())));

        return pollMapper.toRs(user.getPolls());
    }

    @Override
    public Set<PollShortRs> getOpenPolls() {
        log.info(LogMessage.GET_OPEN_POLL);

        return pollMapper.toShortRs(pollRepository.findByDateToAfter(new Date()));
    }

    @Override
    public Set<PollShortRs> getClosePolls() {
        log.info(LogMessage.GET_CLOSE_POLL);

        return pollMapper.toShortRs(pollRepository.findByDateToBefore(new Date()));
    }

    public PollRs getDetailsPoll(Long pollId, User user) throws NotFoundException, ValidationException {
        log.info(LogMessage.GET_DETAILS_POLL, pollId, user.getId());

        Poll poll = getPoll(pollId);
        if (!validate.isAfterNow(poll.getDateFrom()))
            throw new ValidationException(ExceptionMessage.DETAILS_ACCESS_DENIED);
        if (!poll.getUsers().contains(user))
            throw new ValidationException(ExceptionMessage.NOT_SUBSCRIBE);

        poll.getQuestions().forEach(q -> q.getAnswers().removeIf(a -> a.getQuestion().getType().equals(QuestionTypeDto.OPEN)));
        return pollMapper.toRs(poll);
    }

}
