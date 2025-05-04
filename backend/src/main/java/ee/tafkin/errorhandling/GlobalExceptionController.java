package ee.tafkin.errorhandling;

import ee.tafkin.errorhandling.exception.CustomException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionController {

  private final ErrorProperties errorProperties;

  @ExceptionHandler({CustomException.class})
  @ApiResponses(value = {
    @ApiResponse(responseCode = "400", description = "Invalid request input",
      content = @Content),
    @ApiResponse(responseCode = "404", description = "Resource not found",
      content = @Content)})
  public ResponseEntity<Object> handleCustomException(Exception ex) {
    CustomException customException = (CustomException) ex;
    ErrorResponse error = ErrorResponse.builder()
      .message(ex.getMessage())
      .stackTrace(getStackTrace(ex)).build();

    log.error(ex.getMessage(), ex);
    return new ResponseEntity<>(error, customException.getStatusCode());
  }

  @ExceptionHandler({ValidationException.class, MethodArgumentNotValidException.class})
  @ApiResponse(responseCode = "400", description = "Invalid request input")
  public ResponseEntity<Object> handleValidationException(Exception ex) {
    ErrorResponse error = ErrorResponse.builder()
      .message(ex.getMessage())
      .stackTrace(getStackTrace(ex)).build();

    log.error(ex.getMessage(), ex);
    return new ResponseEntity<>(error, BAD_REQUEST);
  }

  private String getStackTrace(Throwable throwable) {
    if (errorProperties.getShowStacktrace()) {
      return ExceptionUtils.getStackTrace(throwable);
    }
    return null;
  }
}
