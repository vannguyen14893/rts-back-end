package com.cmc.recruitment.exception;

public class FileFormatException extends Exception {

  private String            fileName;

  private static final long serialVersionUID = 1L;

  public FileFormatException() {
    super();
  }

  public FileFormatException(final String message) {
    this.fileName = message;
  }

  @Override
  public String getMessage() {
    return this.fileName + " is not a valid format";
  }
}
