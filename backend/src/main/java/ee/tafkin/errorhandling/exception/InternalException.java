package ee.tafkin.errorhandling.exception;

import org.springframework.http.HttpStatus;

public class InternalException extends CustomException {
  public InternalException(String message) {
    super(message);
  }

  @Override
  public HttpStatus getStatusCode() {
    return HttpStatus.INTERNAL_SERVER_ERROR;
  }
}
