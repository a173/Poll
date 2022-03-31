package ru.poll.validates;

import ru.poll.exceptions.ValidationException;
import ru.poll.models.interfaces.IPoll;
import ru.poll.models.interfaces.IQuestion;

import java.util.Date;
import java.util.Set;

public interface Validate {

    <T extends IPoll> T poll(T poll) throws ValidationException;

    <T extends IQuestion> Set<T> questions(Set<T> questions) throws ValidationException;

    <T extends IQuestion> T question(T question) throws ValidationException;

    boolean isAfterNow(Date date);
}
