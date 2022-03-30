package ru.poll.models.interfaces;

import ru.poll.models.dto.QuestionTypeDto;

import java.util.Set;

public interface QuestionInterface {

    String getBody();
    QuestionTypeDto getType();
    <T extends AnswerInterface> Set<T> getAnswers();
}
