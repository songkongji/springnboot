package org.zerock.boardex.controller.advice;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

//REST api 방식은 눈에 보이지 않게 에러를 처리하므로 에러 찾기가 힘들다. 그래서 에러를 눈에 보이도록 하기 위해 작성함.
@RestControllerAdvice
@Log4j2
public class CustomRestAdvice {
//    Controller계층에서 발생하는 에러를 메서드로 처리
    @ExceptionHandler(BindException.class)
//    상태 코드를 변경함 EXPECTATION_FAILED -> 실패를 예상하다
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ResponseEntity<Map<String, String>>
    handleBindException(BindException e){
        log.error(e);
//        Map배열로 errorMap 객체 생성
        Map<String, String> errorMap = new HashMap<>();
//        만약 에러가 있다면
        if(e.hasErrors()){
//            에러 객체를 bindingResult에 저장
            BindingResult bindingResult = e.getBindingResult();
//            발생한 에러 개수만큼 반복
//            getField() : 오류 필드명 getCode() : 오류 코드
            bindingResult.getFieldErrors().forEach(filedError->{errorMap.put(filedError.getField(), filedError.getCode());});
        }
        return ResponseEntity.badRequest().body(errorMap);
    }
//    데이터에 문제가 있다고 사용자에게 예외 메시지를 전송하기
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ResponseEntity<Map<String, String>> handleFKException(Exception e) {
        log.error(e);
        Map<String, String> errorMap = new HashMap<>();
//        System.curruntTimeMillis() : 1970년 1월 1일부터 경과한 시간을 long값으로 리턴
        errorMap.put("time","" + System.currentTimeMillis());
        errorMap.put("msg", "constraint fails");
        return ResponseEntity.badRequest().body(errorMap);
    }
}
