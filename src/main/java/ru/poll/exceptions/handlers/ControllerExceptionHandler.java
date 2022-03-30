package ru.poll.exceptions.handlers;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.poll.constants.ExceptionMessage;
import ru.poll.exceptions.DeleteException;
import ru.poll.exceptions.NotFoundException;
import ru.poll.exceptions.UpdateException;
import ru.poll.exceptions.ValidationException;
import ru.poll.models.response.ResponseMessage;

@Log4j2
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseMessage handlerBindException(BindException ex) {
        return ResponseMessage.builder()
                .message(ex.getBindingResult().getFieldErrors())
                .errorCode(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseMessage handlerValidationException(ValidationException ex) {
        return ResponseMessage.builder()
                .message(ex.getMessage())
                .errorCode(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    @ExceptionHandler(UpdateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseMessage handlerUpdateException(UpdateException ex) {
        return ResponseMessage.builder()
                .message(ex.getMessage())
                .errorCode(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    @ExceptionHandler(DeleteException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseMessage handlerDeleteException(DeleteException ex) {
        return ResponseMessage.builder()
                .message(ex.getMessage())
                .errorCode(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseMessage handlerNotFoundException(NotFoundException ex) {
        return ResponseMessage.builder()
                .message(ex.getMessage())
                .errorCode(HttpStatus.NOT_FOUND.value())
                .build();
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseMessage handlerException(RuntimeException ex) {
        log.info(ex.getMessage());

        return ResponseMessage.builder()
                .message(ExceptionMessage.INTERNAL_ERROR)
                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
    }
}
