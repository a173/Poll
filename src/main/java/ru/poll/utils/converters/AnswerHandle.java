package ru.poll.utils.converters;

import ru.poll.exceptions.NotFoundException;
import ru.poll.exceptions.ValidationException;
import ru.poll.models.entity.Answer;
import ru.poll.models.entity.Question;

import java.util.Set;

public interface AnswerHandle {

    void handle(Object content, Set<Answer> answers, Question question) throws ValidationException, NotFoundException;

    String getType();
}
