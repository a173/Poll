package ru.poll.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.poll.models.entity.Poll;

import java.util.Date;
import java.util.Set;

public interface PollRepository extends JpaRepository<Poll, Long> {

    Set<Poll> findByDateToAfter(Date date);

    Set<Poll> findByDateToBefore(Date date);

}
