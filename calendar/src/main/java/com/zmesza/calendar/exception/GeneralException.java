package com.zmesza.calendar.exception;

import org.springframework.http.HttpStatus;

public class GeneralException extends Exception {

  private ErrorMessage errorMessage;
  private HttpStatus httpStatus;

  public GeneralException() {}

  public GeneralException(String error) {
    this.errorMessage = new ErrorMessage(error);
  }

  public GeneralException(String error, HttpStatus httpStatus) {
    this.errorMessage = new ErrorMessage(error);
    this.httpStatus = httpStatus;
  }

  public ErrorMessage getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(ErrorMessage errorMessage) {
    this.errorMessage = errorMessage;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  public void setHttpStatus(HttpStatus httpStatus) {
    this.httpStatus = httpStatus;
  }
}
