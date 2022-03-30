package ru.poll.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.poll.exceptions.NotFoundException;
import ru.poll.exceptions.ValidationException;
import ru.poll.models.entity.User;
import ru.poll.models.response.PollRs;
import ru.poll.models.response.PollShortRs;
import ru.poll.services.PollService;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/poll")
public class PollController {

    private final PollService pollService;

    @GetMapping(value = "/open")
    public Set<PollShortRs> getOpenPoll() {
        return pollService.getOpenPolls();
    }

    @GetMapping(value = "/close")
    public Set<PollShortRs> getClosePoll() {
        return pollService.getClosePolls();
    }

    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @GetMapping(value = "/details/{id}")
    public PollRs getDetailsPoll(@PathVariable(name = "id") Long id) throws ValidationException, NotFoundException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return pollService.getDetailsPoll(id, user);
    }
}
