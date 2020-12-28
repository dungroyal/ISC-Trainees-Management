package com.ISC.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.ISC.project.payload.ResultRespon;


@RestControllerAdvice
public class ApiExceptionHandler{
    /**
     * Tất cả các Exception không được khai báo sẽ được xử lý tại đây
     */
	
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.OK)
    public ResultRespon handleAllException(Exception ex, WebRequest request) {
        // quá trình kiểm soat lỗi diễn ra ở đây
    	if(ex.getLocalizedMessage().startsWith("could not execute statement; SQL [n/a]; constraint [UK_76a7f3cl1evph68tkgfivirbk]")) {
    		return new ResultRespon(1, "Duplicate Student Code");
    	}
    	if(ex.getLocalizedMessage().startsWith("could not execute statement; SQL [n/a]; constraint [UK_8hyd9vyx1ttnb2kdpog7bljf8]")) {
    		return new ResultRespon(1, "Duplicate Student Email");
    	}
    	
        return new ResultRespon(1, ex.getLocalizedMessage());
    }
}
