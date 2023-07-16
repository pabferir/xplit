package dev.pabferir.api;

import org.axonframework.commandhandling.CommandExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class ExceptionController {
    private static final Logger log = LoggerFactory.getLogger(ExceptionController.class);

    @ExceptionHandler({
            IllegalStateException.class,
            IllegalArgumentException.class,
            CommandExecutionException.class
    })
    private Mono<ResponseEntity<ProblemDetail>> handleBadRequest(Exception exception) {
        log.error("An error occurred while processing a request: {}", exception.getMessage());

        return Mono.just(ResponseEntity.badRequest()
                                       .body(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                                                                              exception.getMessage())));
    }

    @ExceptionHandler(Exception.class)
    private Mono<ResponseEntity<ProblemDetail>> handleInternalServerError(Exception exception) {
        log.error("An error occurred while processing a request: {}", exception.getMessage());

        return Mono.just(ResponseEntity.internalServerError()
                                       .body(ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR)));
    }
}
