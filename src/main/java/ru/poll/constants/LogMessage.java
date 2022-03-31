package ru.poll.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LogMessage {

    public static final String GET_USER_BY_USERNAME = "Получение пользователя: {} из БД";
    public static final String CREATE_POLL = "Создавние опроса: {}";
    public static final String UPDATE_POLL = "Обновление опроса с id: {}";
    public static final String DELETE_POLL = "Удаление опроса с id: {}";
    public static final String GET_OPEN_POLL = "Получение списка открытых опросов";
    public static final String GET_CLOSE_POLL = "Получение списка закрытых опросов";
    public static final String ADD_QUESTIONS_TO_POLL = "Добавление вопросов к опросу: {}";
    public static final String UPDATE_QUESTION = "Обновление вопроса с id: {}";
    public static final String ADD_ANSWERS_TO_QUESTION = "Добавление ответов к вопросу: {}";
    public static final String DELETE_QUESTION = "Удаление вопроса с id: {}";
    public static final String UPDATE_ANSWER = "Обновление ответа с id: {}";
    public static final String DELETE_ANSWER = "Удаление ответа с id: {}";
    public static final String GET_DETAILS_POLL = "Получение детальной информации по опросу {}, пользователем {}";
    public static final String SUBSCRIBE = "Пользователь {} подписывается на опрос {}";
    public static final String UNSUBSCRIBE = "Пользователь {} отписывается от опроса {}";
    public static final String GET_SUBSCRIPTIONS = "Получение списка опросов пользователем {}";
    public static final String GET_SUBSCRIPTIONS_DETAILS = "Получение списка опросов с ответами пользователя {}";
    public static final String USER_ANSWER = "Пользователь {} отвечает на вопрос {}";

}
