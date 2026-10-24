package com.trustvault.common;
import org.springframework.http.HttpStatus;import org.springframework.security.access.AccessDeniedException;import org.springframework.ui.Model;import org.springframework.web.bind.annotation.*;import java.util.NoSuchElementException;
@ControllerAdvice public class GlobalExceptionHandler {
 @ExceptionHandler(AccessDeniedException.class) @ResponseStatus(HttpStatus.FORBIDDEN) public String forbidden(Exception e,Model m){m.addAttribute("message","You are not allowed to perform this action.");return "error/403";}
 @ExceptionHandler(NoSuchElementException.class) @ResponseStatus(HttpStatus.NOT_FOUND) public String notFound(Exception e,Model m){m.addAttribute("message","The requested resource was not found or is not accessible.");return "error/404";}
 @ExceptionHandler(IllegalArgumentException.class) @ResponseStatus(HttpStatus.BAD_REQUEST) public String badRequest(Exception e,Model m){m.addAttribute("message",e.getMessage());return "error/400";}
}
