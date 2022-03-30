package ru.poll.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.poll.models.entity.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
