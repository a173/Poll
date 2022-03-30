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
import ru.poll.mappers.AnswerMapper;
import ru.poll.models.dto.AnswerDto;
import ru.poll.models.entity.Answer;
import ru.poll.models.entity.Question;
import ru.poll.models.request.AnswerRq;
import ru.poll.models.response.AnswerRs;
import ru.poll.repository.AnswerRepository;
import ru.poll.services.AnswerService;
import ru.poll.utils.Validate;

import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {

    private final AnswerMapper answerMapper;
    private final AnswerRepository answerRepository;
    private final ObjectMapper objectMapper;
    private final Validate validate;

    @Override
    public Question addQuestionToAnswer(Question question, Set<AnswerRq> answers) {
        question.getAnswers().addAll(answers.stream()
                .map(a -> answerMapper.toEntity(a, question))
                .collect(Collectors.toSet()));

        return question;
    }

    @Override
    @Transactional
    public AnswerRs updateAnswer(Long id, JsonPatch jsonPatch) throws NotFoundException, JsonPatchException, JsonProcessingException, UpdateException {
        log.info(LogMessage.UPDATE_ANSWER, id);

        Answer answer = getAnswer(id);
        if (validate.isAfterNow(answer.getQuestion().getPoll().getDateFrom()))
            throw new UpdateException(ExceptionMessage.POLL_ALREADY_STARTED_UPDATE_DENIED);
        JsonNode patched = jsonPatch.apply(objectMapper.convertValue(answerMapper.toDto(answer), JsonNode.class));
        AnswerDto answerPatched = objectMapper.treeToValue(patched, AnswerDto.class);

        return answerMapper.toRs(answerRepository.save(
                answerMapper.toEntity(answerPatched)
        ));
    }

    @Override
    public void deleteAnswer(Long id) throws NotFoundException, DeleteException, ValidationException {
        log.info(LogMessage.DELETE_ANSWER, id);

        Answer answer = getAnswer(id);
        if (validate.isAfterNow(answer.getQuestion().getPoll().getDateFrom()))
            throw new DeleteException(ExceptionMessage.POLL_ALREADY_STARTED_DELETE_DENIED);
        if (answer.getQuestion().getAnswers().size() - 1 < 2)
            throw new ValidationException(ExceptionMessage.CLOSED_QUESTION_LESS_TWO_ANSWER);

        answerRepository.delete(answer);
    }

    @Override
    public Answer getAnswer(Long id) throws NotFoundException {

        return answerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionMessage.ANSWER_NOT_FOUND, id)));
    }

    @Override
    public Answer getAnswer(Question question, Long answerId) throws NotFoundException, ValidationException {
        Answer answerEntity = getAnswer(answerId);
        if (!question.getAnswers().contains(answerEntity))
            throw new ValidationException(ExceptionMessage.ANSWER_NOT_RELATIONSHIP_TO_QUESTION);
        return answerEntity;
    }

    @Override
    public Answer save(String answer, Question question) {
        return answerRepository.save(answerMapper.toEntity(answer, question));
    }
}
