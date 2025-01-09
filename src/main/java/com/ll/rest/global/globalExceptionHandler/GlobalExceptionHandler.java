package com.ll.rest.global.globalExceptionHandler;

import com.ll.rest.global.app.AppConfig;
import com.ll.rest.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.View;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

//밑의 코드는 복사해서 많이 사용한다. (룰을 정하듯이 선언적으로 예외선언 가능)

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final View error;

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<RsData<Void>> handle(NoSuchElementException ex) {

        if (AppConfig.isNotProd()) ex.printStackTrace(); //개발단계에서 오류 출력해줌

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new RsData<>(
                        "404-1",
                        "해당 데이터가 존재하지 않습니다."
                ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RsData<Void>> handle(MethodArgumentNotValidException ex) {

        if (AppConfig.isNotProd()) ex.printStackTrace();

        String message = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .filter(error -> error instanceof FieldError)
                .map(error -> (FieldError) error)
                .map(error -> error.getField() + "-" + error.getCode() + "-" + error.getDefaultMessage())
                .sorted(Comparator.comparing(String::toString))
                .collect(Collectors.joining("\n"));


        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new RsData<>(
                        "400-1",
                        message + " : " + message
                ));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<RsData<Void>> handle(DataIntegrityViolationException ex) {

        if (AppConfig.isNotProd()) ex.printStackTrace(); //개발단계에서 오류 출력해줌

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new RsData<>(
                        "400-1",
                        "이미 존재하는 데이터 입니다."
                ));
    }
}
