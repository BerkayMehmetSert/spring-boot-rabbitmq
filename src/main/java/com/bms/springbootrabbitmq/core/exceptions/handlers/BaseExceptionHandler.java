package com.bms.springbootrabbitmq.core.exceptions.handlers;

import com.bms.springbootrabbitmq.core.exceptions.BusinessException;
import com.bms.springbootrabbitmq.core.exceptions.NotFoundException;
import com.bms.springbootrabbitmq.core.exceptions.ValidationException;
import org.springframework.http.ProblemDetail;

public abstract class BaseExceptionHandler {
    protected abstract ProblemDetail handleException(BusinessException exception);

    protected abstract ProblemDetail handleException(NotFoundException exception);

    protected abstract ProblemDetail handleException(Exception exception);

    protected abstract ProblemDetail handleException(ValidationException exception);
}
