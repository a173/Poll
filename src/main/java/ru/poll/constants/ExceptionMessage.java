package ru.poll.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionMessage {

    public static final String USER_NOT_FOUND = "Пользователь с никнеймом %s не найден";
    public static final String USERID_NOT_FOUND = "Пользователь с id %s не найден";
    public static final String POLL_NOT_FOUND = "Опрос с id %s не найден";
    public static final String POLLS_NOT_FOUND = "Опросы не найдены";
    public static final String QUESTION_NOT_FOUND = "Вопрос с id %s не найден";
    public static final String ANSWER_NOT_FOUND = "Ответ с id %s не найден";
    public static final String POLL_ALREADY_STARTED_UPDATE_DENIED = "Опрос уже стартовал, редактирование запрещено";
    public static final String POLL_ALREADY_STARTED_DELETE_DENIED = "Опрос уже стартовал, удаление запрещено";
    public static final String CLOSED_QUESTION_LESS_TWO_ANSWER = "Закрытый вопрос не может содержать менее 2-х ответов";
    public static final String OPEN_QUESTION_WITH_ANSWER = "Открытый вопрос не может содержать ответов";
    public static final String INCORRECT_DATE_FROM = "Время начала опроса должно быть не раньше текущего времени";
    public static final String INCORRECT_DATE_TO = "Время окончания опроса должно быть больше времени начала";
    public static final String ALREADY_SUBSCRIBE = "Вы уже зарегистрированы на этот опрос";
    public static final String NOT_SUBSCRIBE = "Вы не подписаны на этот опрос";
    public static final String POLL_FINISHED = "Опрос окончен";
    public static final String DETAILS_ACCESS_DENIED = "Опрос не начался, просмотр детализации невозможен";
    public static final String ALREADY_ANSWER = "Вы уже отвечали на этот вопрос";
    public static final String QUESTION_NOT_RELATIONSHIP_TO_POLL = "Вопрос не относится к этому опросу";
    public static final String POLL_NOT_STARTED = "Опрос еще не начался";
    public static final String ANSWER_NOT_RELATIONSHIP_TO_QUESTION = "Ответ не относится к этому вопросу";
    public static final String INTERNAL_ERROR = "Внутренняя ошибка";

}
