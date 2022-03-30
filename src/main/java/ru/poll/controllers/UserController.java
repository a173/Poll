package ru.poll.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.poll.exceptions.NotFoundException;
import ru.poll.exceptions.ValidationException;
import ru.poll.models.entity.User;
import ru.poll.models.request.AnswerObjectRq;
import ru.poll.models.response.PollRs;
import ru.poll.models.response.PollShortRs;
import ru.poll.models.response.UserRs;
import ru.poll.services.UserService;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private static final String BASIC_AUTH = "basicAuth";

    @Operation(security = @SecurityRequirement(name = BASIC_AUTH))
    @GetMapping(value = "/{pollId}/subscribe")
    public UserRs subscribePoll(@PathVariable("pollId") Long pollId) throws NotFoundException, ValidationException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userService.subscribePoll(pollId, user);
    }

    @Operation(security = @SecurityRequirement(name = BASIC_AUTH))
    @GetMapping(value = "/{pollId}/unsubscribe")
    public UserRs unsubscribePoll(@PathVariable("pollId") Long pollId) throws NotFoundException, ValidationException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userService.unsubscribePoll(pollId, user);
    }

    @Operation(security = @SecurityRequirement(name = BASIC_AUTH))
    @GetMapping(value = "/my/subscribe")
    public Set<PollShortRs> mySubscribe() throws NotFoundException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userService.mySubscribe(user);
    }

    @Operation(security = @SecurityRequirement(name = BASIC_AUTH))
    @GetMapping(value = "/my/polls")
    public Set<PollRs> getStartedPolls() throws NotFoundException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userService.getStartedPolls(user);
    }

    @Operation(security = @SecurityRequirement(name = BASIC_AUTH))
    @PostMapping(value = "/started/{pollId}/{questionId}")
    public void beginPoll(@PathVariable(name = "pollId") Long pollId, @PathVariable(name = "questionId") Long questionId, @RequestBody AnswerObjectRq answer) throws NotFoundException, ValidationException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        userService.beginPoll(user, pollId, questionId, answer);
    }
}
