package ru.poll.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import ru.poll.exceptions.DeleteException;
import ru.poll.exceptions.NotFoundException;
import ru.poll.exceptions.UpdateException;
import ru.poll.exceptions.ValidationException;
import ru.poll.models.entity.Answer;
import ru.poll.models.entity.Question;
import ru.poll.models.request.AnswerRq;
import ru.poll.models.response.AnswerRs;

import java.util.Set;

public interface AnswerService {

    Question addQuestionToAnswer(Question question, Set<AnswerRq> answers);

    AnswerRs updateAnswer(Long id, JsonPatch jsonPatch) throws NotFoundException, JsonPatchException, JsonProcessingException, UpdateException;

    void deleteAnswer(Long id) throws NotFoundException, DeleteException, ValidationException;

    Answer getAnswer(Long id) throws NotFoundException;

    Answer getValidationAnswer(Question question, Long answerId) throws NotFoundException, ValidationException;

    Answer save(String answer, Question question);
}
