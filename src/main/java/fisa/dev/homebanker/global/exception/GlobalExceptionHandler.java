package fisa.dev.homebanker.global.exception;

import fisa.dev.homebanker.domain.product.exception.ProductException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ProductException.class)
  public ResponseEntity<ExceptionResponse> productException(ProductException e) {
    ExceptionResponse response = new ExceptionResponse(e.getMsg(), e.getStatus());
    return new ResponseEntity(response, response.getStatus());
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ExceptionResponse> JsonParseException(HttpMessageNotReadableException e) {
    String str = e.getMessage().split(" ")[5];
    str = str.replace(":", "");
    String msg = String.format("입력하신 %s는 자료형에 맞지 않습니다.", str);

    ExceptionResponse response = new ExceptionResponse(msg, HttpStatus.BAD_REQUEST);
    return new ResponseEntity(response, response.getStatus());
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ExceptionResponse> ParameterTypeMismatchException(
      MethodArgumentTypeMismatchException e) {
    String[] split = e.getMessage().split(" ");

    String requiredType = split[10];
    requiredType = requiredType.replace(";", "");
    String inputType = split[6];

    String input = split[14];
    input = input.replace("\\", "");
    input = input.replace("\"", "'");

    String str = String.format("요청 매개변수(%s)의 타입이 %s가 아닌 %s 타입이여야 합니다.", input, inputType,
        requiredType);
    ExceptionResponse response = new ExceptionResponse(str, HttpStatus.BAD_REQUEST);
    return new ResponseEntity(response, response.getStatus());
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<ExceptionResponse> MissingRequestParameterException(
      MissingServletRequestParameterException e) {
    String param = e.getMessage().split(" ")[3];
    String str = String.format("%s 매개변수는 필수 요청값입니다.", param);
    ExceptionResponse response = new ExceptionResponse(str, HttpStatus.BAD_REQUEST);
    return new ResponseEntity(response, response.getStatus());
  }

}
