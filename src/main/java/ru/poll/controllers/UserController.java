package ru.poll.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.poll.exceptions.NotFoundException;
import ru.poll.exceptions.ValidationException;
import ru.poll.models.entities.User;
import ru.poll.models.requests.ObjectRq;
import ru.poll.models.responses.PollRs;
import ru.poll.models.responses.PollShortRs;
import ru.poll.models.responses.UserRs;
import ru.poll.services.UserService;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private static final String BASIC_AUTH = "basicAuth";

    @Operation(security = @SecurityRequirement(name = BASIC_AUTH))
    @PostMapping(value = "/subscribe")
    public ResponseEntity<UserRs> subscribePoll(@Valid @RequestBody ObjectRq pollId) throws NotFoundException, ValidationException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return ResponseEntity.ok(userService.subscribePoll(((Number) pollId.getContent()).longValue(), user));
    }

    @Operation(security = @SecurityRequirement(name = BASIC_AUTH))
    @PostMapping(value = "/unsubscribe")
    public ResponseEntity<UserRs> unsubscribePoll(@Valid @RequestBody ObjectRq pollId)throws NotFoundException, ValidationException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return ResponseEntity.ok(userService.unsubscribePoll(((Number) pollId.getContent()).longValue(), user));
    }

    @Operation(security = @SecurityRequirement(name = BASIC_AUTH))
    @GetMapping(value = "/subscribes")
    public ResponseEntity<Set<PollShortRs>> mySubscribe() throws NotFoundException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return ResponseEntity.ok(userService.mySubscribe(user));
    }

    @Operation(security = @SecurityRequirement(name = BASIC_AUTH))
    @GetMapping(value = "/started/polls")
    public ResponseEntity<Set<PollRs>> getStartedPolls() throws NotFoundException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return ResponseEntity.ok(userService.getStartedPolls(user));
    }

    @Operation(security = @SecurityRequirement(name = BASIC_AUTH))
    @PostMapping(value = "/answer/{pollId}/{questionId}")
    public ResponseEntity<Void> beginPoll(@PathVariable(name = "pollId") Long pollId,
                                          @PathVariable(name = "questionId") Long questionId,
                                          @Valid @RequestBody ObjectRq answer) throws NotFoundException, ValidationException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        userService.beginPoll(user, pollId, questionId, answer);
        return ResponseEntity.ok().build();
    }
}
