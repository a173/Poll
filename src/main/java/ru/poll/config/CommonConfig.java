package ru.poll.config;

import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.poll.utils.converters.AnswerHandle;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class CommonConfig {

    @Bean
    public Map<String, AnswerHandle> answerMap(@NonNull Collection<AnswerHandle> answerHandles) {
        return answerHandles.stream()
                .collect(Collectors.toMap(AnswerHandle::getType, Function.identity()));
    }
}
