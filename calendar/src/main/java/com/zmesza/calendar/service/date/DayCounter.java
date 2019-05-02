package com.zmesza.calendar.service.date;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class DayCounter {

  private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");

  public static long getDayCount(String start, String end) {
    long diff = -1;
    try {

      Date dateStart = simpleDateFormat.parse(start);
      Date dateEnd = simpleDateFormat.parse(end);

      if (isValidDate(start) && isValidDate(end)) {
        diff = Math.round((dateEnd.getTime() - dateStart.getTime()) / (double) 86400000) + 1;
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return diff;
  }

  public static boolean isValidDate(String inputDate) {
    boolean answer = false;
    try {
      Date date = simpleDateFormat.parse(inputDate);
      Date deadLine = java.sql.Date.valueOf(LocalDate.now().plusYears(5));

      answer = date.after(simpleDateFormat.parse("2017.12.31")) && date.before(deadLine);

    } catch (Exception e) {
      e.printStackTrace();
    }

    return answer;
  }
}
