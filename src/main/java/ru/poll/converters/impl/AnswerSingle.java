package ru.poll.converters.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.poll.exceptions.NotFoundException;
import ru.poll.exceptions.ValidationException;
import ru.poll.models.dto.QuestionTypeDto;
import ru.poll.models.entities.Answer;
import ru.poll.models.entities.Question;
import ru.poll.services.AnswerService;
import ru.poll.converters.AnswerHandler;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class AnswerSingle implements AnswerHandler {

    private final ObjectMapper objectMapper;
    private final AnswerService answerService;

    @Override
    public void handle(Object content, Set<Answer> answers, Question question) throws ValidationException, NotFoundException {
        answers.add(answerService.getValidationAnswer(question, objectMapper.convertValue(content, Long.class)));
    }

    @Override
    public String getType() {
        return QuestionTypeDto.SINGLE.name();
    }
}
