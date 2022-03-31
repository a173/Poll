package ru.poll.models.interfaces;

import ru.poll.models.dto.QuestionTypeDto;

import java.util.Set;

public interface IQuestion {

    String getBody();
    QuestionTypeDto getType();
    <T extends IAnswer> Set<T> getAnswers();
}
