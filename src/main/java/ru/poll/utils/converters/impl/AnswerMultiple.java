package ru.poll.utils.converters.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.poll.exceptions.NotFoundException;
import ru.poll.exceptions.ValidationException;
import ru.poll.models.dto.QuestionTypeDto;
import ru.poll.models.entity.Answer;
import ru.poll.models.entity.Question;
import ru.poll.services.AnswerService;
import ru.poll.utils.converters.AnswerHandle;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class AnswerMultiple implements AnswerHandle {

    private final ObjectMapper objectMapper;
    private final AnswerService answerService;

    @Override
    public void handle(Object content, Set<Answer> answers, Question question) throws ValidationException, NotFoundException {
        for (Long answerId : objectMapper.convertValue(content, Long[].class))
            answers.add(answerService.getAnswer(question, answerId));
    }

    @Override
    public String getType() {
        return QuestionTypeDto.MULTIPLE.name();
    }
}
