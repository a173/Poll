package ru.poll.models.interfaces;

import java.util.Date;
import java.util.Set;

public interface PollInterface {

    String getTitle();
    Date getDateFrom();
    Date getDateTo();
    String getDescription();
    <T extends QuestionInterface> Set<T> getQuestions();
}
