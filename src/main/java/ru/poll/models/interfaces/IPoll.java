package ru.poll.models.interfaces;

import java.util.Date;
import java.util.Set;

public interface IPoll {

    String getTitle();
    Date getDateFrom();
    Date getDateTo();
    String getDescription();
    <T extends IQuestion> Set<T> getQuestions();
}
