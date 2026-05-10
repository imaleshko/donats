package com.donats.backend.exceptions;

import org.jspecify.annotations.NonNull;
import org.springframework.http.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ProblemDetail handleUserAlreadyExists(RuntimeException ex) {
        return createProblemDetail(HttpStatus.CONFLICT, "Користувач вже існує", ex.getMessage());
    }

    @ExceptionHandler({EmailAlreadyInUseException.class, UsernameAlreadyInUseException.class})
    public ProblemDetail handleDataInUse(RuntimeException ex) {
        return createProblemDetail(HttpStatus.CONFLICT, "Конфлікт даних", ex.getMessage());
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ProblemDetail handleInvalidPassword(RuntimeException ex) {
        return createProblemDetail(HttpStatus.BAD_REQUEST, "Неправильний пароль", ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ProblemDetail handleUserNotFound(UserNotFoundException ex) {
        return createProblemDetail(HttpStatus.NOT_FOUND, "Користувача не знайдено", ex.getMessage());
    }

    @ExceptionHandler({InvalidTokenException.class, TokenExpiredException.class})
    public ProblemDetail handleTokenExceptions(RuntimeException ex) {
        return createProblemDetail(HttpStatus.UNAUTHORIZED, "Помилка токена", ex.getMessage());
    }

    @ExceptionHandler(SlugAlreadyInUseException.class)
    public ProblemDetail handleSlugAlreadyInUse(RuntimeException ex) {
        return createProblemDetail(HttpStatus.CONFLICT, "Помилка створення збору", ex.getMessage());
    }

    @ExceptionHandler(FundraiserNotFoundException.class)
    public ProblemDetail handleFundraiserNotFound(RuntimeException ex) {
        return createProblemDetail(HttpStatus.NOT_FOUND, "Збір не знайдено", ex.getMessage());
    }

    @ExceptionHandler(DonationInitException.class)
    public ProblemDetail handleDonationInit(RuntimeException ex) {
        return createProblemDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Помилка ініціалізації донату", ex.getMessage());
    }

    @ExceptionHandler(DonationCloseException.class)
    public ProblemDetail handleDonationClose(RuntimeException ex) {
        return createProblemDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Помилка завершення донату", ex.getMessage());
    }

    @ExceptionHandler(ImageUploadException.class)
    public ProblemDetail handleImageUpload(RuntimeException ex) {
        return createProblemDetail(HttpStatus.BAD_REQUEST, "Помилка завантаження зображення", ex.getMessage());
    }

    @ExceptionHandler(ForbiddenException.class)
    public ProblemDetail handleForbidden(RuntimeException ex) {
        return createProblemDetail(HttpStatus.FORBIDDEN, "Немає прав", ex.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ProblemDetail handleBadCredentials() {
        return createProblemDetail(HttpStatus.UNAUTHORIZED, "Помилка авторизації", "Неправильний email або пароль");
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            @NonNull MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request) {

        String detailMessage = ex.getFieldError() != null
                ? ex.getFieldError().getDefaultMessage()
                : "Некоректні вхідні дані";

        return ResponseEntity.badRequest()
                .body(createProblemDetail(HttpStatus.BAD_REQUEST, "Помилка валідації", detailMessage));
    }

    private ProblemDetail createProblemDetail(HttpStatus status, String title, String detail) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, detail);
        problemDetail.setTitle(title);
        return problemDetail;
    }
}
