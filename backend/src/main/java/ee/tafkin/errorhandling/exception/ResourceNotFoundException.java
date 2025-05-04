package ee.tafkin.errorhandling.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends CustomException {

  public ResourceNotFoundException(String message) {
    super(message);
  }

  @Override
  public HttpStatus getStatusCode() {
    return HttpStatus.NOT_FOUND;
  }
}
