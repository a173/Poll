package ru.poll.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.poll.exceptions.DeleteException;
import ru.poll.exceptions.NotFoundException;
import ru.poll.exceptions.UpdateException;
import ru.poll.exceptions.ValidationException;
import ru.poll.models.request.AnswerRq;
import ru.poll.models.request.PollRq;
import ru.poll.models.request.QuestionRq;
import ru.poll.models.response.AnswerRs;
import ru.poll.models.response.PollRs;
import ru.poll.models.response.QuestionRs;
import ru.poll.services.AnswerService;
import ru.poll.services.PollService;
import ru.poll.services.QuestionService;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final PollService pollService;
    private final QuestionService questionService;
    private final AnswerService answerService;

    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @PostMapping(value = "/poll")
    public PollRs savePoll(@Valid @RequestBody PollRq poll) throws ValidationException {
        return pollService.savePoll(poll);
    }

    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @PatchMapping(value = "/poll/{id}", consumes = "application/json-patch+json")
    public PollRs updatePoll(@PathVariable("id") Long id, @RequestBody JsonPatch jsonPatch)
            throws JsonPatchException, NotFoundException, JsonProcessingException, ValidationException, UpdateException {
        return pollService.updatePoll(id, jsonPatch);
    }

    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @DeleteMapping(value = "/poll/{id}")
    public void deletePoll(@PathVariable("id") Long id) throws NotFoundException, DeleteException {
        pollService.deletePoll(id);
    }

    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @PutMapping(value = "/poll/{id}")
    public PollRs addQuestions(@PathVariable("id") Long id, @RequestBody Set<QuestionRq> questions)
            throws ValidationException, NotFoundException, UpdateException {
        return pollService.addQuestions(id, questions);
    }

    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @PatchMapping(value = "/question/{id}", consumes = "application/json-patch+json")
    public QuestionRs updateQuestion(@PathVariable("id") Long id, @RequestBody JsonPatch jsonPatch)
            throws JsonPatchException, NotFoundException, JsonProcessingException, ValidationException, UpdateException {
        return questionService.updateQuestion(id, jsonPatch);
    }

    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @DeleteMapping(value = "/question/{id}")
    public void deleteQuestion(@PathVariable("id") Long id) throws NotFoundException, DeleteException {
        questionService.deleteQuestion(id);
    }

    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @PutMapping(value = "/question/{id}")
    public QuestionRs addAnswers(@PathVariable("id") Long id, @RequestBody Set<AnswerRq> answers)
            throws ValidationException, NotFoundException, UpdateException {
        return questionService.addAnswers(id, answers);
    }

    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @PatchMapping(value = "/answer/{id}", consumes = "application/json-patch+json")
    public AnswerRs updateAnswer(@PathVariable("id") Long id, @RequestBody JsonPatch jsonPatch)
            throws JsonPatchException, NotFoundException, JsonProcessingException, ValidationException, UpdateException {
        return answerService.updateAnswer(id, jsonPatch);
    }

    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @DeleteMapping(value = "/answer/{id}")
    public void deleteAnswer(@PathVariable("id") Long id) throws NotFoundException, DeleteException, ValidationException {
        answerService.deleteAnswer(id);
    }
}
