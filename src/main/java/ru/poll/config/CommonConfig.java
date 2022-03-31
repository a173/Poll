package ru.poll.config;

import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.poll.converters.AnswerHandler;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class CommonConfig {

    @Bean
    public Map<String, AnswerHandler> answerMap(@NonNull Collection<AnswerHandler> answerHandles) {
        return answerHandles.stream()
                .collect(Collectors.toMap(AnswerHandler::getType, Function.identity()));
    }
}
