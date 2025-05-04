package ee.tafkin.errorhandling.exception;

import org.springframework.http.HttpStatus;

public abstract class CustomException extends RuntimeException {
  public CustomException(String message) {
    super(message);
  }

  public CustomException() {
    super();
  }

  public abstract HttpStatus getStatusCode();

  public int getHttpStatus() {
    return getStatusCode().value();
  }
}
