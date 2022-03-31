package ru.poll.converters;

import ru.poll.exceptions.NotFoundException;
import ru.poll.exceptions.ValidationException;
import ru.poll.models.entities.Answer;
import ru.poll.models.entities.Question;

import java.util.Set;

public interface AnswerHandler {

    void handle(Object content, Set<Answer> answers, Question question) throws ValidationException, NotFoundException;

    String getType();
}
