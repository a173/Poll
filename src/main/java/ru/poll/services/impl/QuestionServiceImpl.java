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
import ru.poll.mappers.QuestionMapper;
import ru.poll.models.dto.QuestionDto;
import ru.poll.models.dto.QuestionTypeDto;
import ru.poll.models.entities.Poll;
import ru.poll.models.entities.Question;
import ru.poll.models.requests.AnswerRq;
import ru.poll.models.requests.QuestionRq;
import ru.poll.models.responses.QuestionRs;
import ru.poll.repository.QuestionRepository;
import ru.poll.services.AnswerService;
import ru.poll.services.QuestionService;
import ru.poll.validates.Validate;

import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
    private final ObjectMapper objectMapper;
    private final Validate validate;
    private final AnswerService answerService;

    @Override
    public Poll addPollToQuestions(Poll poll, Set<QuestionRq> questions) throws ValidationException {
        poll.getQuestions().addAll(validate.questions(questions).stream()
                .map(q -> questionMapper.toEntity(q, poll))
                .collect(Collectors.toSet()));

        return poll;
    }

    @Override
    @Transactional
    public QuestionRs updateQuestion(Long id, JsonPatch jsonPatch) throws NotFoundException, JsonPatchException, JsonProcessingException, ValidationException, UpdateException {
        log.info(LogMessage.UPDATE_QUESTION, id);

        Question question = getQuestion(id);
        if (validate.isAfterNow(question.getPoll().getDateFrom()))
            throw new UpdateException(ExceptionMessage.POLL_ALREADY_STARTED_UPDATE_DENIED);
        JsonNode patched = jsonPatch.apply(objectMapper.convertValue(questionMapper.toDto(question), JsonNode.class));
        QuestionDto questionPatched = objectMapper.treeToValue(patched, QuestionDto.class);

        return questionMapper.toRs(questionRepository.save(
                questionMapper.toEntity(validate.question(questionPatched))
        ));
    }

    @Override
    public void deleteQuestion(Long id) throws NotFoundException, DeleteException {
        log.info(LogMessage.DELETE_QUESTION, id);

        Question question = getQuestion(id);
        if (validate.isAfterNow(question.getPoll().getDateFrom()))
            throw new DeleteException(ExceptionMessage.POLL_ALREADY_STARTED_DELETE_DENIED);

        questionRepository.delete(question);
    }

    @Override
    @Transactional
    public QuestionRs addAnswers(Long id, Set<AnswerRq> answers) throws NotFoundException, UpdateException, ValidationException {
        log.info(LogMessage.ADD_ANSWERS_TO_QUESTION, id);

        Question question = getQuestion(id);
        if (validate.isAfterNow(question.getPoll().getDateFrom()))
            throw new UpdateException(ExceptionMessage.POLL_ALREADY_STARTED_UPDATE_DENIED);
        if (question.getType().equals(QuestionTypeDto.OPEN))
            throw new ValidationException(ExceptionMessage.OPEN_QUESTION_WITH_ANSWER);

        return questionMapper.toRs(questionRepository.save(answerService.addQuestionToAnswer(question, answers)));
    }

    @Override
    public Question getQuestion(Long id) throws NotFoundException {

        return questionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionMessage.QUESTION_NOT_FOUND, id)));
    }
}
