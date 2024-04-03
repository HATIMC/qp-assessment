package com.hatim.qp.controller.advice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.hatim.qp.controller.advice.exception.GroceryException;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(GroceryException.class)
    public ResponseEntity<Map<String, Object>> groceryException(GroceryException e) {
	e.printStackTrace();
	return ResponseEntity.badRequest().body(new HashMap<String, Object>() {
	    private static final long serialVersionUID = 1L;
	    {
		put("code", e.getErrorCode());
		put("error", e.getMessage());
	    }
	});
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> exception(Exception e) {
	e.printStackTrace();
	return ResponseEntity.badRequest().body(new HashMap<String, String>() {
	    private static final long serialVersionUID = 1L;
	    {
		put("error", e.getMessage());
	    }
	});
    }
}
