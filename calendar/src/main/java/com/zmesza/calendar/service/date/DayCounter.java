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
      Date deadLine = java.sql.Date.valueOf(LocalDate.now().plusYears(5));

      if (dateStart.after(simpleDateFormat.parse("2018.01.01")) && dateEnd.before(deadLine)) {
        diff = Math.round((dateEnd.getTime() - dateStart.getTime()) / (double) 86400000) + 1;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return diff;
  }
}
