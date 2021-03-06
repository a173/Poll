package ru.poll.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.poll.models.entities.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
