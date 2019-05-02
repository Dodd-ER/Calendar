package com.zmesza.calendar.service.date;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Iterator;

public class DayManipulator {

  private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
  private static String startDate = "2018.01.01.";

  private static long[] officialHolidays_getDayIntTheYear = readFromJson("holidays.json");

  private static long[] readFromJson(String filename) {

    JSONParser parser = new JSONParser();

    try {
      Object obj = parser.parse(new FileReader(filename));
      JSONObject jsonObject = (JSONObject) obj;
      long dayInTheYearFromJson = (long)jsonObject.get("dayInTheYear");



    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }
//      {1, 74, 89, 92, 121, 141, 232, 296, 305, 359, 360};

  public static long getDayCount(String start, String end) {
    long diff = -1;
    try {

      Date dateToStart = simpleDateFormat.parse(start);
      Date dateToEnd = simpleDateFormat.parse(end);

      if (isValidDate(start) && isValidDate(end)) {
        diff = Math.round((dateToEnd.getTime() - dateToStart.getTime()) / (double) 86400000) + 1;
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

  public static boolean isRestDayMethod(String inputDate) {
    long dayInTheYear = getDayCount(inputDate.substring(0,4).concat(".01.01."), inputDate);
    long dayAfterStartDate = getDayCount(startDate, inputDate);

    if (dayAfterStartDate % 7 == 0 || dayAfterStartDate % 7 == 6) {
      return true;
    }

    for (long officialHoliday : officialHolidays_getDayIntTheYear) {
      if (officialHoliday == dayInTheYear) {
        return true;
      }
    }

    return false;
  }

  public static long checkingMethodForWeekendDaysBetween(String date1, String date2) {
    return ((getDayCount(date1, date2) / 7) * 2)
        + ((getDayCount(date1, date2) % 7) / 6 );
  }

  public static long howManyRestDaysBetweenMethod(String date1, String date2) {

    long numOfWeekendDaysBetweenStartAndDate1 = checkingMethodForWeekendDaysBetween(startDate, date1);
    long numOfWeekendDaysBetweenStartAndDate2 = checkingMethodForWeekendDaysBetween(startDate, date2);

    return numOfWeekendDaysBetweenStartAndDate2 - numOfWeekendDaysBetweenStartAndDate1;
  }

  public static long howManyWorkDaysBetweenMethod(String date1, String date2) {
    long numOfDaysBetweenInputDates = getDayCount(date1, date2);

    return numOfDaysBetweenInputDates - howManyRestDaysBetweenMethod(date1, date2);
  }
}
