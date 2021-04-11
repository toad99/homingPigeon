package fr.homingpigeon.common;

import fr.homingpigeon.common.exception.BusinessException;
import fr.homingpigeon.common.exception.ForbiddenException;
import fr.homingpigeon.common.exception.ValidationErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class GlobalAdviceController {

    //TODO : enlever ExceptionHandler de EntityNotFoundException et gerer les 404 a la main
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> onEntityNotFoundException(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<String> onBusinessException(Exception exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(ValidationErrorException.class)
    public ResponseEntity<List<ValidationError>> onValidationErrorException(ValidationErrorException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getValidationErrors());
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<String> onAccessDeniedException(Exception exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exception.getMessage());
    }
}
