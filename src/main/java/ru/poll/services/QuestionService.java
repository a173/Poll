package ru.poll.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import ru.poll.exceptions.DeleteException;
import ru.poll.exceptions.NotFoundException;
import ru.poll.exceptions.UpdateException;
import ru.poll.exceptions.ValidationException;
import ru.poll.models.entities.Poll;
import ru.poll.models.entities.Question;
import ru.poll.models.requests.AnswerRq;
import ru.poll.models.requests.QuestionRq;
import ru.poll.models.responses.QuestionRs;

import java.util.Set;

public interface QuestionService {

    Poll addPollToQuestions(Poll poll, Set<QuestionRq> questions) throws ValidationException;

    QuestionRs updateQuestion(Long id, JsonPatch jsonPatch) throws NotFoundException, JsonPatchException, JsonProcessingException, ValidationException, UpdateException;

    void deleteQuestion(Long id) throws NotFoundException, DeleteException;

    QuestionRs addAnswers(Long id, Set<AnswerRq> answers) throws NotFoundException, UpdateException, ValidationException;

    Question getQuestion(Long id) throws NotFoundException;
}
