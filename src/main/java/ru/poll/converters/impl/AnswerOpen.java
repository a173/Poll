package ru.poll.converters.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.poll.models.dto.QuestionTypeDto;
import ru.poll.models.entities.Answer;
import ru.poll.models.entities.Question;
import ru.poll.services.AnswerService;
import ru.poll.converters.AnswerHandler;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class AnswerOpen implements AnswerHandler {

    private final ObjectMapper objectMapper;
    private final AnswerService answerService;

    @Override
    public void handle(Object content, Set<Answer> answers, Question question) {
        answers.add(answerService
                .save(objectMapper.convertValue(content, String.class), question));
    }

    @Override
    public String getType() {
        return QuestionTypeDto.OPEN.name();
    }
}
