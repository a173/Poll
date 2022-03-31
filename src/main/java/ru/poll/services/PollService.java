package ru.poll.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import ru.poll.exceptions.DeleteException;
import ru.poll.exceptions.NotFoundException;
import ru.poll.exceptions.UpdateException;
import ru.poll.exceptions.ValidationException;
import ru.poll.models.entities.Poll;
import ru.poll.models.entities.User;
import ru.poll.models.requests.PollRq;
import ru.poll.models.requests.QuestionRq;
import ru.poll.models.responses.PollRs;
import ru.poll.models.responses.PollShortRs;

import java.util.Set;

public interface PollService {

    PollRs savePoll(PollRq pollRq) throws ValidationException;

    PollRs updatePoll(Long id, JsonPatch jsonPatch) throws NotFoundException, JsonPatchException, JsonProcessingException, ValidationException, UpdateException;

    void deletePoll(Long id) throws NotFoundException, DeleteException;

    Set<PollShortRs> getOpenPolls();

    Set<PollShortRs> getClosePolls();

    PollRs addQuestions(Long id, Set<QuestionRq> questions) throws NotFoundException, UpdateException, ValidationException;

    Poll getPoll(Long id) throws NotFoundException;

    Set<PollShortRs> getPollsShortRs(Set<Poll> polls) throws NotFoundException;

    Set<PollRs> getPollsRs(User user) throws NotFoundException;

    PollRs getDetailsPoll(Long pollId, User user) throws NotFoundException, ValidationException;
}
