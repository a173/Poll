package ru.poll.validates.impl;

import org.springframework.stereotype.Component;
import ru.poll.constants.ExceptionMessage;
import ru.poll.exceptions.ValidationException;
import ru.poll.models.dto.QuestionTypeDto;
import ru.poll.models.interfaces.IPoll;
import ru.poll.models.interfaces.IQuestion;
import ru.poll.validates.Validate;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Component
public class ValidateImpl implements Validate {

    public <T extends IPoll> T poll(T poll) throws ValidationException {
        if (new Date().compareTo(poll.getDateFrom()) > -1)
            throw new ValidationException(ExceptionMessage.INCORRECT_DATE_FROM);
        if (poll.getDateFrom().compareTo(poll.getDateTo()) > 0)
            throw new ValidationException(ExceptionMessage.INCORRECT_DATE_TO);
        if (Objects.nonNull(poll.getQuestions()))
            questions(poll.getQuestions());

        return poll;
    }

    public <T extends IQuestion> Set<T> questions(Set<T> questions) throws ValidationException {
        for (T question : questions) {
            question(question);
        }

        return questions;
    }

    public <T extends IQuestion> T question(T question) throws ValidationException {
        if (question.getType().equals(QuestionTypeDto.OPEN) &&
                Objects.nonNull(question.getAnswers()) &&
                question.getAnswers().size() > 0)
            throw new ValidationException(ExceptionMessage.OPEN_QUESTION_WITH_ANSWER);
        if (!question.getType().equals(QuestionTypeDto.OPEN) &&
                (Objects.isNull(question.getAnswers()) || question.getAnswers().size() < 2))
            throw new ValidationException(ExceptionMessage.CLOSED_QUESTION_LESS_TWO_ANSWER);

        return question;
    }

    public boolean isAfterNow(Date date) {
        return date.before(new Date());
    }

}
