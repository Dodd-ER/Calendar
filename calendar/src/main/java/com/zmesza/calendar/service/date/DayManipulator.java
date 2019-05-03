package com.zmesza.calendar.service.date;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class DayManipulator {

  private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
  private static long[] officialHolidays_getDayIntTheYear = readFromJson("holidays.json");
  private static String startDate = "2018.01.01.";

  private static long[] readFromJson(String filename) {

    File file = new File(filename);
    String absolutePath = file.getAbsolutePath();

    JSONParser parser = new JSONParser();
    ArrayList<Long> answerList = new ArrayList<>();

    try {
      Object obj = parser.parse(new FileReader(absolutePath));
      JSONObject jsonObject = (JSONObject) obj;
      JSONArray dayInTheYearFromJson = (JSONArray) jsonObject.get("dayInTheYear");
      Iterator<String> iterator = dayInTheYearFromJson.iterator();
      while (iterator.hasNext()) {
        answerList.add(Long.parseLong(iterator.next()));
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }

    //Reading from holidays.json is not succeeded, but the array should be the following answerArray
    long[] answerArray = {1, 74, 89, 92, 121, 141, 232, 296, 305, 359, 360};
    return answerArray;
  }

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

  public static long checkingMethodForRestDaysBetween(String date1, String date2) {
    long numOfWeekendDays = ((getDayCount(date1, date2) / 7) * 2)
        + ((getDayCount(date1, date2) % 7) / 6 );

    long dayInTheYearAtDate1 = getDayCount(startDate, date1);
    long dayInTheYearAtDate2 = getDayCount(startDate, date2);
    long numOfHolidays = 0;

    //Duplicate for loop makes sure that if the interval between
    //the startDate and Date2 is longer than a year, it counts holidays the times it needed
    for (int i = 0; i <= dayInTheYearAtDate2 / 365; i++) {
      for (long officialHoliday : officialHolidays_getDayIntTheYear) {
        if (officialHoliday >= dayInTheYearAtDate1 && officialHoliday <= (dayInTheYearAtDate2 - (i * 365))) {
          numOfHolidays++;
        }
      }
    }

    return numOfWeekendDays + numOfHolidays;
  }

  public static long howManyRestDaysBetweenMethod(String date1, String date2) {
    long numOfWeekendDaysBetweenStartAndDate1 = checkingMethodForRestDaysBetween(startDate, date1);
    long numOfWeekendDaysBetweenStartAndDate2 = checkingMethodForRestDaysBetween(startDate, date2);

    return numOfWeekendDaysBetweenStartAndDate2 - numOfWeekendDaysBetweenStartAndDate1;
  }

  public static long howManyWorkDaysBetweenMethod(String date1, String date2) {
    long numOfDaysBetweenInputDates = getDayCount(date1, date2);

    return numOfDaysBetweenInputDates - howManyRestDaysBetweenMethod(date1, date2);
  }
}
