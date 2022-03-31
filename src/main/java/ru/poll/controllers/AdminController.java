package ru.poll.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    private static final String APPLICATION_PATCH = "application/json-patch+json";
    private static final String BASIC_AUTH = "basicAuth";

    @Operation(security = @SecurityRequirement(name = BASIC_AUTH))
    @PostMapping(value = "/poll")
    public PollRs savePoll(@Valid @RequestBody PollRq poll) throws ValidationException {
        return pollService.savePoll(poll);
    }

    @Operation(security = @SecurityRequirement(name = BASIC_AUTH))
    @PatchMapping(value = "/poll/{id}", consumes = APPLICATION_PATCH)
    public ResponseEntity<PollRs> updatePoll(@PathVariable("id") Long id,
                                            @RequestBody JsonPatch jsonPatch)
            throws JsonPatchException, NotFoundException, JsonProcessingException, ValidationException, UpdateException {
        return ResponseEntity.ok(pollService.updatePoll(id, jsonPatch));
    }

    @Operation(security = @SecurityRequirement(name = BASIC_AUTH))
    @DeleteMapping(value = "/poll/{id}")
    public ResponseEntity<Void> deletePoll(@PathVariable("id") Long id) throws NotFoundException, DeleteException {
        pollService.deletePoll(id);
        return ResponseEntity.ok().build();
    }

    @Operation(security = @SecurityRequirement(name = BASIC_AUTH))
    @PutMapping(value = "/poll/{id}")
    public ResponseEntity<PollRs> addQuestions(@PathVariable("id") Long id,
                               @Valid @RequestBody Set<QuestionRq> questions)
            throws ValidationException, NotFoundException, UpdateException {
        return ResponseEntity.ok(pollService.addQuestions(id, questions));
    }

    @Operation(security = @SecurityRequirement(name = BASIC_AUTH))
    @PatchMapping(value = "/question/{id}", consumes = APPLICATION_PATCH)
    public ResponseEntity<QuestionRs> updateQuestion(@PathVariable("id") Long id,
                                     @RequestBody JsonPatch jsonPatch)
            throws JsonPatchException, NotFoundException, JsonProcessingException, ValidationException, UpdateException {
        return ResponseEntity.ok(questionService.updateQuestion(id, jsonPatch));
    }

    @Operation(security = @SecurityRequirement(name = BASIC_AUTH))
    @DeleteMapping(value = "/question/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable("id") Long id) throws NotFoundException, DeleteException {
        questionService.deleteQuestion(id);
        return ResponseEntity.ok().build();
    }

    @Operation(security = @SecurityRequirement(name = BASIC_AUTH))
    @PutMapping(value = "/question/{id}")
    public ResponseEntity<QuestionRs> addAnswers(@PathVariable("id") Long id,
                                 @Valid @RequestBody Set<AnswerRq> answers)
            throws ValidationException, NotFoundException, UpdateException {
        return ResponseEntity.ok(questionService.addAnswers(id, answers));
    }

    @Operation(security = @SecurityRequirement(name = BASIC_AUTH))
    @PatchMapping(value = "/answer/{id}", consumes = APPLICATION_PATCH)
    public ResponseEntity<AnswerRs> updateAnswer(@PathVariable("id") Long id,
                                 @RequestBody JsonPatch jsonPatch)
            throws JsonPatchException, NotFoundException, JsonProcessingException, UpdateException {
        return ResponseEntity.ok(answerService.updateAnswer(id, jsonPatch));
    }

    @Operation(security = @SecurityRequirement(name = BASIC_AUTH))
    @DeleteMapping(value = "/answer/{id}")
    public ResponseEntity<Void> deleteAnswer(@PathVariable("id") Long id) throws NotFoundException, DeleteException, ValidationException {
        answerService.deleteAnswer(id);
        return ResponseEntity.ok().build();
    }
}
