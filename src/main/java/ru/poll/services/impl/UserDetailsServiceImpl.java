package ru.poll.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.poll.constants.ExceptionMessage;
import ru.poll.constants.LogMessage;
import ru.poll.repository.UserRepository;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String nickname) {
        log.info(LogMessage.GET_USER_BY_USERNAME, nickname);

        return userRepository.findByUsernameIgnoreCase(nickname)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(ExceptionMessage.USER_NOT_FOUND, nickname)));
    }
}
