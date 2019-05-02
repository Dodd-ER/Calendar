package com.zmesza.calendar.service.date;

import java.util.regex.Pattern;

class FormattedDateMatcher implements DateMatcher {

  private static Pattern DATE_PATTERN = Pattern.compile(
      "^\\d{4}\\.\\d{2}\\.\\d{2}\\.$");

  @Override
  public boolean matches(String date) {
    return DATE_PATTERN.matcher(date).matches();
  }
}
